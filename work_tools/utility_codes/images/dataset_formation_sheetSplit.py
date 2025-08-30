import os
import random
import shutil
import concurrent.futures
from tqdm import tqdm
import threading
import json


"""
This script organizes images into a dataset structure for training and testing.
It now splits by p_id from a metadata JSON file, sampling TEST_SHEETS p_ids for the test set.
Files with those p_ids are moved to the test set, others to the train set.
Metadata is filtered to contain only HUMAN images.

Hyperparameters:
- HUMAN_POOL_DIR: Directory containing HUMAN images
- AI_POOL_DIR: Directory containing AI images
- OUTPUT_DIR: Output directory for train/test splits
- METADATA_JSON: Path to metadata JSON file
- TEST_SHEETS: Number of p_ids to sample for test set
"""


# Hyperparameters
HUMAN_POOL_DIR = "Java_hum_formated_preproc"
AI_POOL_DIR = "Java_ai_formated_preproc"
OUTPUT_DIR = "output"
METADATA_JSON = "metadata.json"  # Path to metadata JSON file
TEST_SHEETS = 169  # Number of p_ids to sample for test set


def create_readme(directory):
    readme_content = "Label descriptions:\n0 - Human codes\n1 - AI generated codes"
    with open(os.path.join(directory, "readme.txt"), "w") as f:
        f.write(readme_content)


def move_file(src, dst, pbar, lock):
    os.makedirs(os.path.dirname(dst), exist_ok=True)
    shutil.move(src, dst)
    with lock:
        pbar.update(1)


def gather_png_files(root_dir):
    png_files = []
    for dirpath, dirnames, filenames in os.walk(root_dir):
        if "bad" in dirpath:
            continue
        for filename in filenames:
            if filename.lower().endswith(".png"):
                png_files.append(os.path.join(dirpath, filename))
    return png_files


def organize_images_by_pid(
    human_dir, ai_dir, base_output_dir, metadata_json, test_sheets=1000
):
    # Create main output directories
    os.makedirs(base_output_dir, exist_ok=True)
    test_dir = os.path.join(base_output_dir, "test")
    train_dir = os.path.join(base_output_dir, "train")
    os.makedirs(test_dir, exist_ok=True)
    os.makedirs(train_dir, exist_ok=True)

    # Create readme files
    create_readme(test_dir)
    create_readme(train_dir)

    # Load metadata and filter for HUMAN images only (Java)
    with open(metadata_json, "r") as f:
        metadata = json.load(f)
    human_metadata = [
        entry for entry in metadata if entry["language"].lower() == "java"
    ]

    # Extract all unique p_ids from filtered metadata
    all_pids = set(entry["p_id"] for entry in human_metadata)
    test_pids = set(random.sample(list(all_pids), min(test_sheets, len(all_pids))))
    train_pids = all_pids - test_pids

    # Build sets for fast lookup using relative paths
    # Gather all PNG files and their relative paths
    human_files = gather_png_files(human_dir)
    # Map s_id.png to all possible relative paths (handle duplicates)
    from collections import defaultdict

    sid_to_relpaths = defaultdict(list)
    for f in human_files:
        relpath = os.path.relpath(f, human_dir)
        basename = os.path.basename(f)
        sid_to_relpaths[basename].append(relpath)
    # Build mapping from relative path to p_id
    human_filename_to_pid = {}
    for entry in human_metadata:
        key = f"{entry['s_id']}P.png"
        if key in sid_to_relpaths:
            # If multiple files match, pick the first
            human_filename_to_pid[sid_to_relpaths[key][0]] = entry["p_id"]
    human_filenames = set(human_filename_to_pid.keys())

    # Debug: print first 10 basenames found in HUMAN image pool
    print(
        "Sample HUMAN image basenames:", [os.path.basename(f) for f in human_files][:10]
    )
    # Debug: print first 10 expected basenames from metadata
    print(
        "Sample expected basenames from metadata:",
        [f"{entry['s_id']}.png" for entry in human_metadata][:10],
    )

    # Helper to get p_id from filename (for AI images, p_id encoded in filename)
    def get_pid_from_filename(filename):
        # Example: filename format: chatgpt_p00000_0P.png
        parts = filename.split("_")
        for part in parts:
            if part.startswith("p") and part[1:].isdigit():
                return part
        return None

    # Move HUMAN images
    human_test_dir = os.path.join(test_dir, "0")
    human_train_dir = os.path.join(train_dir, "0")
    os.makedirs(human_test_dir, exist_ok=True)
    os.makedirs(human_train_dir, exist_ok=True)

    # Build sets for test/train files using relative paths
    test_human_files = [
        f
        for f in human_files
        if os.path.relpath(f, human_dir) in human_filenames
        and human_filename_to_pid[os.path.relpath(f, human_dir)] in test_pids
    ]
    train_human_files = [
        f
        for f in human_files
        if os.path.relpath(f, human_dir) in human_filenames
        and human_filename_to_pid[os.path.relpath(f, human_dir)] in train_pids
    ]

    print(test_human_files[0:10])

    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
        lock = threading.Lock()
        with tqdm(
            total=len(test_human_files), desc="Moving HUMAN test set files"
        ) as pbar:
            futures = [
                executor.submit(
                    move_file,
                    f,
                    os.path.join(human_test_dir, os.path.basename(f)),
                    pbar,
                    lock,
                )
                for f in test_human_files
            ]
            concurrent.futures.wait(futures)
        with tqdm(
            total=len(train_human_files), desc="Moving HUMAN train set files"
        ) as pbar:
            futures = [
                executor.submit(
                    move_file,
                    f,
                    os.path.join(human_train_dir, os.path.basename(f)),
                    pbar,
                    lock,
                )
                for f in train_human_files
            ]
            concurrent.futures.wait(futures)

    # Move AI images
    ai_test_dir = os.path.join(test_dir, "1")
    ai_train_dir = os.path.join(train_dir, "1")
    os.makedirs(ai_test_dir, exist_ok=True)
    os.makedirs(ai_train_dir, exist_ok=True)

    ai_files = gather_png_files(ai_dir)
    # Map basename to all possible relative paths (handle duplicates)
    from collections import defaultdict

    ai_basename_to_relpaths = defaultdict(list)
    for f in ai_files:
        relpath = os.path.relpath(f, ai_dir)
        basename = os.path.basename(f)
        ai_basename_to_relpaths[basename].append(relpath)
    # Debug: print first 10 basenames found in AI image pool
    print("Sample AI image basenames:", [os.path.basename(f) for f in ai_files][:10])
    # Debug: print first 10 expected p_id values
    print("Sample expected p_id values:", list(test_pids)[:10])
    # Build sets for test/train files using relative paths and p_id extracted from filename
    test_ai_files = []
    train_ai_files = []
    for f in ai_files:
        relpath = os.path.relpath(f, ai_dir)
        basename = os.path.basename(f)
        pid = get_pid_from_filename(basename)
        if pid in test_pids:
            test_ai_files.append(f)
        elif pid in train_pids:
            train_ai_files.append(f)
    with concurrent.futures.ThreadPoolExecutor(max_workers=8) as executor:
        lock = threading.Lock()
        with tqdm(total=len(test_ai_files), desc="Moving AI test set files") as pbar:
            futures = [
                executor.submit(
                    move_file,
                    f,
                    os.path.join(ai_test_dir, os.path.basename(f)),
                    pbar,
                    lock,
                )
                for f in test_ai_files
            ]
            concurrent.futures.wait(futures)
        with tqdm(total=len(train_ai_files), desc="Moving AI train set files") as pbar:
            futures = [
                executor.submit(
                    move_file,
                    f,
                    os.path.join(ai_train_dir, os.path.basename(f)),
                    pbar,
                    lock,
                )
                for f in train_ai_files
            ]
            concurrent.futures.wait(futures)

    # Save filtered metadata for HUMAN images only
    with open(os.path.join(base_output_dir, "human_metadata.json"), "w") as f:
        json.dump(human_metadata, f, indent=2)


if __name__ == "__main__":
    organize_images_by_pid(
        HUMAN_POOL_DIR, AI_POOL_DIR, OUTPUT_DIR, METADATA_JSON, TEST_SHEETS
    )
