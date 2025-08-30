import json  # For reading and writing JSON metadata
import os  # For file and directory operations
import numpy as np  # For array and image processing
from PIL import Image  # For image creation and saving
from tqdm import tqdm  # For progress bars
from pathlib import Path  # For file path operations
from concurrent.futures import (
    ProcessPoolExecutor,
    as_completed,
)  # For parallel processing
import multiprocessing  # For CPU count
import concurrent.futures  # For thread-based parallelism
import threading  # For thread locks
import random  # For random sampling

"""
This file takes Class and Class1 input dirs and formates image based dataset split, ready for training. 
This is binary split and intended to be used for our derived CodeNet-based dataset.

Picks all the files in the randomly picked TEST_SHEETS number of exercise sheets. 
This is to prevent duplicate code samples from appearing in both training and testing datasets.
"""

# === Hyperparameters and Directory Setup ===

META_JSON_DIR = (
    "Java_5perEx_codes_hum_raw/metadata.json"  #   # Path to metadata JSON file
)
OUTPUT_DIR = (
    "data_multiclass-sheetSplit_preproc"  # Output directory for images and info
)
HUMAN_DIR = "Java_5perEx_codes_hum_preproc"  # Directory containing human code files
AI_DIR = "Java_20perEx_codes_ai_preproc"  # Directory containing AI code files
TEST_SHEETS = 169  # Number of test sheets to sample

LABEL_CLASS_DIR_PAIRS = [
    (f"human", 0, HUMAN_DIR),
    (f"chatgpt", 1, AI_DIR),
    (f"claude", 2, AI_DIR),
    (f"gemini", 3, AI_DIR),
    (f"deepseek", 4, AI_DIR),
]

BORDER_HEIGHT = 2  # Border height for images
PADDING_HEIGHT_LIMIT = 224  # Images with height < 224 are padded

# === Byte boundaries for image width categories ===
boundaries = {
    32: {"low": 0, "high": 10000},  # 0-10 kB
    64: {"low": 10000, "high": 30000},  # 10-30 kB
    128: {"low": 30000, "high": 60000},  # 30-60 kB
    256: {"low": 60000, "high": 100000},  # 60-100 kB
    384: {"low": 100000, "high": 200000},  # 100-200 kB
    512: {"low": 200000, "high": 500000},  # 200-500 kB
    768: {"low": 500000, "high": 1000000},  # 500kB-1MB
    1024: {"low": 1000000, "high": float("inf")},  # >1MB
}


def get_origin(filename):
    """
    Determine the origin of the code file based on its filename.
    Returns a string label (human, ai, chatgpt, etc).
    """
    lower = filename.lower()
    if "hum" in lower or "human" in lower:
        return "human"
    elif "ai" in lower or "artificial" in lower:
        return "ai"
    elif "chatgpt" in lower:
        return "chatgpt"
    elif "claude" in lower:
        return "claude"
    elif "gemini" in lower:
        return "gemini"
    elif "deepseek" in lower:
        return "deepseek"
    else:
        return "unknown"


def code_to_image(filename, border=2, width=32):
    """
    Convert a code file to an RGB image representation.
    Each row is width*3 bytes, padded as needed. Returns image and metadata.
    """
    with open(filename, "rb") as file:
        binary_data = file.read()
    data = np.frombuffer(binary_data, dtype=np.uint8)
    row_size = width * 3
    height = int(np.ceil(len(data) / row_size))
    min_height = width

    # Pad image if height is below threshold
    if height < PADDING_HEIGHT_LIMIT and height < min_height:
        padding = row_size * min_height - len(data)
    else:
        remainder = len(data) % row_size
        padding = row_size - remainder if remainder else 0

    data = np.pad(data, (0, padding), mode="constant", constant_values=0)
    final_height = max(min_height, height)
    image_data = data.reshape((final_height, width, 3))
    image = Image.fromarray(image_data.astype(np.uint8), "RGB")

    origin = get_origin(filename)

    # Determine image quality state
    if height > width:
        state = "over"
    elif border < height <= width:
        state = "good"
    else:
        state = "bad"
    return {
        "image": image,
        "filename": os.path.splitext(os.path.basename(filename))[0],
        "height": height,
        "origin": origin,
        "state": state,
    }


def find_java(input_dir):
    """
    Recursively find all .java files in the input directory.
    Returns a list of file paths.
    """
    return [str(file) for file in Path(input_dir).rglob("*.java")]


def process_single_code(args):
    """
    Process a single code file: convert to image, determine width by file size, and return metadata.
    Skips files already processed.
    """
    dir, border_height, output_dir, processed_set = args
    dir = dir.replace("\\", "/")
    if dir in processed_set:
        return None

    size = os.path.getsize(dir)
    width = 32  # Default width

    # Select width based on file size boundaries
    for key, value in boundaries.items():
        if value["low"] <= size < value["high"]:
            width = key
            break

    img_info = code_to_image(dir, border=border_height, width=width)
    state = img_info["state"]
    base_name = img_info["filename"]

    return {
        "image": img_info["image"],
        "img_name": f"{base_name}.png",
        "directory": dir,
        "width": width,
        "height": img_info["height"],
        "quality": state,
        "origin": img_info["origin"],
    }


def process_codes(input_dir, output_dir, border_height=BORDER_HEIGHT):
    """
    Process all .java files in the input directory:
    - Converts each to an image
    - Stores metadata in info.json
    - Returns a list of image and metadata tuples
    """
    try:
        with open(f"{output_dir}/info.json", "r") as f:
            codes_info = json.load(f)
            processed_set = set(v["directory"] for v in codes_info.values())
    except FileNotFoundError:
        codes_info = {}
        processed_set = set()

    dirs = find_java(input_dir)
    print(f"Files found: {len(dirs)}")
    images = {}

    # Prepare arguments for parallel processing
    args_list = [
        (filename, border_height, output_dir, processed_set)
        for filename in dirs
        if filename.replace("\\", "/") not in processed_set
    ]

    # Process files in parallel
    with ProcessPoolExecutor(max_workers=multiprocessing.cpu_count()) as executor:
        futures = [executor.submit(process_single_code, args) for args in args_list]

        images = []

        with tqdm(total=len(args_list), desc="Processing codes") as pbar:
            for future in as_completed(futures):
                result = future.result()
                if result:
                    img_name = result["img_name"]
                    codes_info[img_name] = {
                        "directory": result["directory"],
                        "width": result["width"],
                        "height": result["height"],
                        "quality": result["quality"],
                        "origin": result["origin"],
                    }

                    images.append(
                        [
                            result["img_name"],
                            result["image"],
                            result["origin"],
                            result["quality"],
                            result["directory"],
                        ]
                    )

                    pbar.update(1)

    # Save metadata to info.json
    with open(f"{output_dir}/info.json", "w") as f:
        json.dump(codes_info, f, indent=4)

    return images


def create_readme(directory, label_names):
    readme_content = "Label descriptions:\n"
    for tup in label_names:
        readme_content += f"{tup[1]} - {tup[0]}\n"
    with open(os.path.join(directory, "readme.txt"), "w") as f:
        f.write(readme_content)


def save_image(image, path, pbar):
    """
    Save an image to disk and update the progress bar.
    """
    image.save(path)
    pbar.update(1)


def organize_images(
    class0_dir, class1_dir, base_output_dir, input_json, sheet_number=100
):
    """
    Main function to organize images into train/test sets by label.
    - Processes code files into images
    - Splits into train/test sets
    - Copies images to appropriate folders
    """
    # Create main output directories
    os.makedirs(base_output_dir, exist_ok=True)
    test_dir = os.path.join(base_output_dir, "test")
    train_dir = os.path.join(base_output_dir, "train")
    os.makedirs(test_dir, exist_ok=True)
    os.makedirs(train_dir, exist_ok=True)

    # Create readme files describing labels
    create_readme(test_dir, label_names=LABEL_CLASS_DIR_PAIRS)
    create_readme(train_dir, label_names=LABEL_CLASS_DIR_PAIRS)

    images_0 = process_codes(LABEL_CLASS_DIR_PAIRS[0][2], base_output_dir)
    images_1 = process_codes(LABEL_CLASS_DIR_PAIRS[1][2], base_output_dir)

    # Dictionary to store files by their labels
    label_files = {
        LABEL_CLASS_DIR_PAIRS[0][1]: [item for item in images_0 if item[3] != "bad"]
    }

    for tup in LABEL_CLASS_DIR_PAIRS[1:]:
        label_files[tup[1]] = [
            item for item in images_1 if item[3] != "bad" and tup[0] in item[0]
        ]

    # Check if any human images are preprocessed (end with 'P')
    preprocessed_flag = False
    for item in label_files[0]:
        if item[0].split(".")[0].endswith("P"):
            preprocessed_flag = True
            break

    json_obj = None
    SHEETS = set()  # Unique sheet IDs
    hum_test_files = set()  # Human test file IDs

    # Load metadata JSON
    with open(input_json, "r") as f:
        data = f.read()
        json_obj = json.loads(data)

    # Preprocess sheet IDs and s_id values
    for dic in json_obj:
        SHEETS.add(dic["p_id"])
        if preprocessed_flag:
            dic["s_id"] += "P"

    # Randomly sample test sheets
    TEST_SHEETS = random.sample(list(SHEETS), sheet_number)
    temp = set(TEST_SHEETS)

    # Collect human test file IDs
    for dic in json_obj:
        if dic["p_id"] in temp:
            hum_test_files.add(dic["s_id"])

    # Process each category
    for label, lst in label_files.items():
        # Create category directories in both test and train
        test_category_dir = os.path.join(test_dir, str(label))
        train_category_dir = os.path.join(train_dir, str(label))
        os.makedirs(test_category_dir, exist_ok=True)
        os.makedirs(train_category_dir, exist_ok=True)

        # Select random samples for test set
        if len(lst) >= sheet_number * 5:
            # lst -> [[item_name, image, image_origin, image_quality, directory], ...]
            test_files = [
                i
                for i in lst
                if any(sheet in i[4] for sheet in TEST_SHEETS)
                or i[0].split(".")[0] in hum_test_files
            ]
            train_files = [t for t in lst if t not in test_files]

        else:
            test_files = lst
            train_files = []

        # Copy images to test/train directories using threads
        with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
            lock = threading.Lock()
            # Copy test files
            with tqdm(total=len(test_files), desc="Moving test set files") as pbar:
                futures = [
                    executor.submit(
                        save_image,
                        lst[1],  # [img_name, img, img_origin, img_quality, directory]
                        os.path.join(test_category_dir, lst[0]),
                        pbar,
                    )
                    for lst in test_files
                ]
                concurrent.futures.wait(futures)
            # Copy train files
            with tqdm(total=len(train_files), desc="Moving train set files") as pbar:
                futures = [
                    executor.submit(
                        save_image,
                        lst[1],  # [img_name, img, img_origin, img_quality, directory]
                        os.path.join(train_category_dir, lst[0]),
                        pbar,
                    )
                    for lst in train_files
                ]
                concurrent.futures.wait(futures)


if __name__ == "__main__":
    # Entry point: organize images from human and AI directories into train/test sets
    organize_images(HUMAN_DIR, AI_DIR, OUTPUT_DIR, META_JSON_DIR, TEST_SHEETS)
