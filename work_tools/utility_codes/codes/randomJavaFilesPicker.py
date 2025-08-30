import os
import random

"""
This code randomly selects NUM_FILES Java files from INPUT_DIR and outputs them into folder OUTPUT_DIR.
"""

INPUT_DIR = "."
NUM_FILES = 500
OUTPUT_DIR = "random_java_files"


def find_java_files(root_dir):
    """Find all Java files in the given directory."""
    java_files = []
    for dirpath, _, filenames in os.walk(root_dir):
        for fname in filenames:
            if fname.endswith(".java"):
                java_files.append(os.path.join(dirpath, fname))
    return java_files


def select_random_files(java_files, num_files):
    """Select a random sample of Java files."""
    if len(java_files) <= num_files:
        return java_files
    return random.sample(java_files, num_files)


def save_random_files(output_dir, selected_files):
    """Save the selected Java files to the output directory."""
    os.makedirs(output_dir, exist_ok=True)
    for file in selected_files:
        dest_path = os.path.join(output_dir, os.path.basename(file))
        with open(file, 'r', encoding='utf-8') as src_file:
            content = src_file.read()
        with open(dest_path, 'w', encoding='utf-8') as dest_file:
            dest_file.write(content)


if __name__ == "__main__":
    files = find_java_files(INPUT_DIR)
    selected_files = select_random_files(files, NUM_FILES)
    save_random_files(OUTPUT_DIR, selected_files)
