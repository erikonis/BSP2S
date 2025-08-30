import json
import os
import numpy as np
from PIL import Image
from tqdm import tqdm
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor, as_completed
import multiprocessing

"""
Takes INPUT_DIR with Java files within and transforms all Java codes (not recursively) to image representation. Constructs a tidely
organized dataset with images placed in corresponding categories.
"""

INPUT_DIR = "."
OUTPUT_DIR = "output"
BORDER_HEIGHT = 2  # Boundary image height, at which (and below) the image is considered as too small (i.e. bad)
PADDING_HEIGHT_LIMIT = 224  # The height below which we apply padding.

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
    if "chatgpt" in lower:
        return "chatgpt"
    elif "claude" in lower:
        return "claude"
    elif "gemini" in lower:
        return "gemini"
    elif "deepseek" in lower:
        return "deepseek"
    elif "ai" in lower:
        return "ai"
    elif "hum" in lower or "human" in lower:
        return "human"
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


def save_image(image, filename):
    image.save(filename)


def find_java(input_dir):
    return [
        str(file) for file in Path(input_dir).glob("*.java")
    ]  # replace .glob("*.java") to .rglob("*.java") to iterate through all subdirs.


def process_single_code(args):
    filename, border_height, output_dir, processed_set = args
    filename = filename.replace("\\", "/")
    if filename in processed_set:
        return None

    size = os.path.getsize(filename)
    width = 32

    for key, value in boundaries.items():
        if value["low"] <= size < value["high"]:
            width = key
            break

    img_info = code_to_image(filename, border=border_height, width=width)
    state = img_info["state"]
    base_name = img_info["filename"]

    os.makedirs(f"{output_dir}/{width}_{state}", exist_ok=True)
    save_path = f"{output_dir}/{width}_{state}/{base_name}.png"
    save_image(img_info["image"], save_path)

    return {
        "img_name": f"{base_name}.png",
        "directory": base_name,
        "width": width,
        "height": img_info["height"],
        "quality": state,
        "origin": img_info["origin"],
    }


def process_codes(input_dir, output_dir, border_height):
    try:
        with open(f"{output_dir}/info.json", "r") as f:
            codes_info = json.load(f)
            processed_set = set(v["directory"] for v in codes_info.values())
    except FileNotFoundError:
        codes_info = {}
        processed_set = set()

    dirs = find_java(input_dir)
    print(f"Files found: {len(dirs)}")

    args_list = [
        (filename, border_height, output_dir, processed_set)
        for filename in dirs
        if filename.replace("\\", "/") not in processed_set
    ]

    with ProcessPoolExecutor(max_workers=multiprocessing.cpu_count()) as executor:
        futures = [executor.submit(process_single_code, args) for args in args_list]

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
                    pbar.update(1)

    with open(f"{output_dir}/info.json", "w") as f:
        json.dump(codes_info, f, indent=4)


def main():
    process_codes(INPUT_DIR, OUTPUT_DIR, border_height=BORDER_HEIGHT)


if __name__ == "__main__":
    main()
