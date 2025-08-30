import json
import os
import numpy as np
from PIL import Image
from tqdm import tqdm
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor, as_completed
import multiprocessing
import os
import concurrent.futures
from tqdm import tqdm
import threading

"""
This file takes Class and Class1 input dirs and formates image based dataset split, ready for training. 
This is binary split and intended to be used for AIvsSTD database (old dataset that we had used before).

Picks all the files in the specified exercise sheet (lab1-10). This is to prevent duplicate code samples from appearing in both training and testing datasets.
"""


# Hyper parameters
OUTPUT_DIR = "output"
HUMAN_DIR = "AIvsSTD_std_raw"
AI_DIR = "AIvsSTD_ai_raw"
SAMPLES_PER_CATEGORY = 300  # 3388 * 2 = 6776 == 10% * 67760 images in total

BORDER_HEIGHT = 2
PADDING_HEIGHT_LIMIT = 224  # The height below which we apply padding.

TEST_SHEETS = ["lab8"]

# CATEGORIES (BOUNDARIES - in bytes):
boundaries = {
    32: {
        "low": 0,  # 0 bytes
        "high": 10000,  # 10 kB
    },
    64: {
        "low": 10000,  # 10 kB
        "high": 30000,  # 30 kB
    },
    128: {
        "low": 30000,  # 30 kB
        "high": 60000,  # 60 kB
    },
    256: {
        "low": 60000,  # 60 kB
        "high": 100000,  # 100 kB
    },
    384: {
        "low": 100000,  # 100 kB
        "high": 200000,  # 200 kB
    },
    512: {
        "low": 200000,  # 200 kB
        "high": 500000,  # 500 kB
    },
    768: {
        "low": 500000,  # 500 kB
        "high": 1000000,  # 1 MB
    },
    1024: {
        "low": 1000000,  # 1 MB
        "high": float("inf"),  # No upper limit
    },
}


def get_origin(filename):
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
    with open(filename, "rb") as file:
        binary_data = file.read()
    data = np.frombuffer(binary_data, dtype=np.uint8)
    row_size = width * 3
    height = int(np.ceil(len(data) / row_size))
    min_height = width

    if (
        height < PADDING_HEIGHT_LIMIT and height < min_height
    ):  # Images with h<224 are padded to their width
        padding = row_size * min_height - len(data)
    else:
        remainder = len(data) % row_size
        padding = row_size - remainder if remainder else 0

    data = np.pad(data, (0, padding), mode="constant", constant_values=0)
    final_height = max(min_height, height)
    image_data = data.reshape((final_height, width, 3))
    image = Image.fromarray(image_data.astype(np.uint8), "RGB")

    origin = get_origin(filename)

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
    return [
        str(file) for file in Path(input_dir).rglob("*.java")
    ]  # replace .glob("*.java") to .rglob("*.java") to iterate through all subdirs.


def process_single_code(args):
    dir, border_height, output_dir, processed_set = args
    dir = dir.replace("\\", "/")
    if dir in processed_set:
        return None

    size = os.path.getsize(dir)
    width = 32

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

    args_list = [
        (filename, border_height, output_dir, processed_set)
        for filename in dirs
        if filename.replace("\\", "/") not in processed_set
    ]

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

    with open(f"{output_dir}/info.json", "w") as f:
        json.dump(codes_info, f, indent=4)

    return images


def create_readme(directory):
    readme_content = "Label descriptions:\n0 - Human codes\n1 - AI generated codes"
    with open(os.path.join(directory, "readme.txt"), "w") as f:
        f.write(readme_content)


def save_image(image, path, pbar):
    image.save(path)
    pbar.update(1)


def organize_images(class0_dir, class1_dir, base_output_dir, samples_per_category=1000):
    # Create main output directories
    os.makedirs(base_output_dir, exist_ok=True)
    test_dir = os.path.join(base_output_dir, "test")
    train_dir = os.path.join(base_output_dir, "train")
    os.makedirs(test_dir, exist_ok=True)
    os.makedirs(train_dir, exist_ok=True)

    # Create readme files
    create_readme(test_dir)
    create_readme(train_dir)

    # Dictionary to store files by their labels
    label_files = {"0": [], "1": []}

    # Scan source directory and categorize files

    # process_codes -> [img_name, image, image_origin, image_quality, directory]

    label_files["0"] = [
        item for item in process_codes(class0_dir, base_output_dir) if item[3] != "bad"
    ]
    label_files["1"] = [
        item for item in process_codes(class1_dir, base_output_dir) if item[3] != "bad"
    ]

    # Process each category (0 and 1)
    for label, lst in label_files.items():
        # Create category directories in both test and train
        test_category_dir = os.path.join(test_dir, label)
        train_category_dir = os.path.join(train_dir, label)
        os.makedirs(test_category_dir, exist_ok=True)
        os.makedirs(train_category_dir, exist_ok=True)

        # Select random samples for test set
        if len(lst) >= samples_per_category:

            # lst -> [[item_name, image, image_origin, image_quality, directory], ...]

            test_files = [i for i in lst if any(sheet in i[4] for sheet in TEST_SHEETS)]
            train_files = [t for t in lst if t not in test_files]
        else:
            print("NOT ENOUGH SAMPLES FOR CATEGORY: ", label)
            test_files = lst
            train_files = []

        prefix = "ai" if label == "1" else "human"

        with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
            lock = threading.Lock()
            # Copy test files
            with tqdm(total=len(test_files), desc="Moving test set files") as pbar:
                futures = [
                    executor.submit(
                        save_image,
                        lst[1],  # [img_name, img, img_origin, img_quality, directory]
                        os.path.join(test_category_dir, f"{prefix}_test_img_{i}.png"),
                        pbar,
                    )
                    for i, lst in enumerate(test_files)
                ]
                concurrent.futures.wait(futures)
            # Copy train files
            with tqdm(total=len(train_files), desc="Moving train set files") as pbar:
                futures = [
                    executor.submit(
                        save_image,
                        lst[1],  # [img_name, img, img_origin, img_quality, directory]
                        os.path.join(train_category_dir, f"{prefix}_train_img_{i}.png"),
                        pbar,
                    )
                    for i, lst in enumerate(train_files)
                ]
                concurrent.futures.wait(futures)


if __name__ == "__main__":
    organize_images(HUMAN_DIR, AI_DIR, OUTPUT_DIR, SAMPLES_PER_CATEGORY)
