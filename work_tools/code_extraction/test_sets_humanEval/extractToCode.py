import torch
from PIL import Image
from tqdm import tqdm
import numpy as np
import pandas as pd
import random
import os

DATASET_FILENAMES = [
    "csv/humaneval_chatgpt_java_merged.csv",
    "csv/humaneval_chatgpt4_java_merged.csv",
    "csv/humaneval_gemini_java_merged.csv",
]


def load_data(filename):
    """
    Reads a csv data file and returns the dataset.
    """
    return pd.read_csv(filename)


def read_data(dataset):
    codes, labels = [], []
    for i, item in dataset.iterrows():
        codes.append(item[1])
        labels.append(item[2])
    return codes, labels


def code_to_image(code: str, filename: str, border=2, width=32):
    """
    Convert code to an image and save it.

    :param code: The code to convert (string).
    :param filename: The name of the file to save the image to.
    :param border: The border size (default is 2).
    :param width: The width of the image (default is 32).
    """
    binary_data = code.encode("utf-8")
    data = np.frombuffer(binary_data, dtype=np.uint8)
    row_size = width * 3
    height = int(np.ceil(len(data) / row_size))
    min_height = width

    if (
        height < 224 and height < min_height
    ):  # Images with h<224 are padded to their width
        padding = row_size * min_height - len(data)
    else:
        remainder = len(data) % row_size
        padding = row_size - remainder if remainder else 0

    data = np.pad(data, (0, padding), mode="constant", constant_values=0)
    final_height = max(min_height, height)
    image_data = data.reshape((final_height, width, 3))
    image = Image.fromarray(image_data.astype(np.uint8), "RGB")
    image.save(filename)


if __name__ == "__main__":
    offset = 0

    os.makedirs("output", exist_ok=True)

    for dir_name in DATASET_FILENAMES:
        dataset = load_data(dir_name)

        codes, labels = read_data(dataset)
        for i, code in enumerate(codes):
            filename = (
                "output/"
                + (
                    f"{dir_name.split("_")[1]}_ai_img{i+offset}"
                    if labels[i] == "lm"
                    else f"{dir_name.split("_")[1]}_human_img{i}"
                )
                + ".java"
            )
            with open(filename, "w", encoding="utf-8") as f:
                if not pd.isna(code):
                    f.write(code)

        offset += len(codes)
