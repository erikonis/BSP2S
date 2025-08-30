import os
import shutil
from concurrent.futures import ThreadPoolExecutor, as_completed
from tqdm import tqdm

"""
Goes through given INPUT location, copies all png images and pool, paste in a single OUTPUT directory.
"""

INPUT = ""  # "" == current working directory
OUTPUT = "image_pool"


def get_files_to_copy(Input: str, Output: str):
    Input = os.path.abspath(Input)
    Output = os.path.abspath(Output)
    files = []
    for direct in os.listdir(Input):
        direct_path = os.path.join(Input, direct)
        if os.path.isdir(direct_path) and "bad" not in direct and direct_path != Output:
            for file in os.listdir(direct_path):
                src = os.path.join(direct_path, file)
                dst = os.path.join(Output, file)
                if os.path.isfile(src):
                    files.append((src, dst))
    return files


def copy_file(src_dst):
    src, dst = src_dst
    shutil.copyfile(src, dst)


def gather_files(Input: str = "", Output: str = "output"):
    os.makedirs(Output, exist_ok=True)
    files = get_files_to_copy(Input, Output)
    with ThreadPoolExecutor() as executor:
        list(
            tqdm(executor.map(copy_file, files), total=len(files), desc="Copying files")
        )


if __name__ == "__main__":
    gather_files(INPUT, OUTPUT)
