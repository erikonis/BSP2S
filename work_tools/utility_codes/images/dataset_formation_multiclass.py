import os
import random
import shutil
import concurrent.futures
from tqdm import tqdm
import threading
from pathlib import Path

"""
This script organizes images into a dataset structure for training and testing.
It checks the provided POOl directories for according categories, scans them.
Then the `test` and `train` directories are created with subdirectories for each category
(in particular, `0` for human codes and `1` for AI generated codes).

Pooled images are moved into the respective directories.

NOTE:
- Set HUMAN_POOL_DIR and AI_POOL_DIR to the directories containing your images before running.
- If there are fewer images than SAMPLES_PER_CATEGORY in a category, all images will be moved to the test set!
"""


# Example usage:
# LABEL_DIR_PAIRS = [("Human", "Java_20perEx_img_hum_preproc_pool"), ("AI", "Java_20perEx_img_ai_preproc_pool")]
# OUTPUT_DIR = "output"
# SAMPLES_PER_CATEGORY = 3388
# organize_images_multiclass(LABEL_DIR_PAIRS, OUTPUT_DIR, SAMPLES_PER_CATEGORY)

postfix = "preproc"  # raw or preproc

LABEL_DIR_PAIRS = [
    (f"human", f"Java_5perEx_img_hum_{postfix}"),
    (f"chatgpt", f"chatgpt_{postfix}"),
    (f"claude", f"claude_{postfix}"),
    (f"gemini", f"gemini_{postfix}"),
    (f"deepseek", f"deepseek_{postfix}"),
]
OUTPUT_DIR = f"data_{postfix}"

SAMPLES_PER_CATEGORY = 847  # 847 * 5 = 4235 == 10% of 42350 = 5 * 8470


def create_readme(directory, label_names):
    readme_content = "Label descriptions:\n"
    for idx, name in enumerate(label_names):
        readme_content += f"{idx} - {name}\n"
    with open(os.path.join(directory, "readme.txt"), "w") as f:
        f.write(readme_content)


def move_file(src, dst, pbar, lock):
    shutil.move(src, dst)
    with lock:
        pbar.update(1)


def organize_images_multiclass(
    label_dir_pairs, base_output_dir, samples_per_category=1000
):
    # label_dir_pairs: list of (label_name, dir_path)
    # Create main output directories
    os.makedirs(base_output_dir, exist_ok=True)
    test_dir = os.path.join(base_output_dir, "test")
    train_dir = os.path.join(base_output_dir, "train")
    os.makedirs(test_dir, exist_ok=True)
    os.makedirs(train_dir, exist_ok=True)

    label_names = [label for label, _ in label_dir_pairs]
    create_readme(test_dir, label_names)
    create_readme(train_dir, label_names)

    # Dictionary to store files by their label index
    label_files = {str(idx): [] for idx in range(len(label_dir_pairs))}

    # Scan source directories and categorize files
    for idx, (label, dir_path) in enumerate(label_dir_pairs):

        # Collect all .png files recursively, skipping subdirs with 'bad' in their path
        for file in Path(dir_path).rglob("*.png"):
            if "bad" not in str(file.parent):
                label_files[str(idx)].append((str(file), file.name))

    # Process each category
    for idx, tups in label_files.items():
        # Create category directories in both test and train
        test_category_dir = os.path.join(test_dir, str(idx))
        train_category_dir = os.path.join(train_dir, str(idx))
        os.makedirs(test_category_dir, exist_ok=True)
        os.makedirs(train_category_dir, exist_ok=True)

        # Select random samples for test set
        if len(tups) >= samples_per_category:
            test_files = random.sample(tups, samples_per_category)
            train_files = [t for t in tups if t not in test_files]
        else:
            print(f"NOT ENOUGH SAMPLES FOR CATEGORY: {label_names[int(idx)]}")
            test_files = tups
            train_files = []

        with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
            lock = threading.Lock()
            # Move test files
            with tqdm(
                total=len(test_files),
                desc=f"Moving test set files for label {label_names[int(idx)]} (idx {idx})",
            ) as pbar:
                futures = [
                    executor.submit(
                        move_file,
                        tup[0],
                        os.path.join(test_category_dir, tup[1]),
                        pbar,
                        lock,
                    )
                    for tup in test_files
                ]
                concurrent.futures.wait(futures)
            # Move train files
            with tqdm(
                total=len(train_files),
                desc=f"Moving train set files for label {label_names[int(idx)]} (idx {idx})",
            ) as pbar:
                futures = [
                    executor.submit(
                        move_file,
                        tup[0],
                        os.path.join(train_category_dir, tup[1]),
                        pbar,
                        lock,
                    )
                    for tup in train_files
                ]
                concurrent.futures.wait(futures)


if __name__ == "__main__":
    organize_images_multiclass(LABEL_DIR_PAIRS, OUTPUT_DIR, SAMPLES_PER_CATEGORY)
