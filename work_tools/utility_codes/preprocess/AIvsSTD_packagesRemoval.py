"""
This script removes Java package declarations from source files in a directory tree.

It recursively processes all .java files in the specified input directory, removes any 'package' statements, trims trailing whitespace, and eliminates leading/trailing blank lines. Cleaned files are saved either in-place or to a specified output directory, optionally renaming files if output is not in-place.

Usage:
- Set INPUT to the directory containing Java files.
- Set OUTPUT to 'inplace' to overwrite originals, or specify a directory for cleaned files.
- Run the script to preprocess all Java files for package removal and formatting cleanup.
"""

from pathlib import Path
import re, os, shutil
from concurrent.futures import ThreadPoolExecutor, as_completed

import tqdm



INPUT = "AIvsSTD_std_preproc"  # empty string means current directory
OUTPUT = "inplace"  # "inplace" -> overwrites input directory


def remove_packages(code: str) -> str:
    lines = code.split("\n")
    result = []
    for line in lines:
        # Remove package declarations
        if line.strip().startswith("package "):
            continue
        result.append(line)
    return "\n".join(result)


def process_single_file(filename: str, input_dir: str, output_dir: str):
    with open(filename, "r", encoding="utf-8") as file:
        java_code = file.read()

    cleaned_code = remove_packages(java_code)

    lines = [
        line.rstrip() for line in cleaned_code.splitlines()
    ]  # Remove trailing whitespace from each line

    # Remove leading blank lines
    while lines and lines[0] == "":
        lines.pop(0)
    # Remove trailing blank lines
    while lines and lines[-1] == "":
        lines.pop()

    relative_path = os.path.relpath(filename, input_dir)

    output_file = os.path.join(
        output_dir,
        (
            relative_path.replace(".java", "P.java")
            if input_dir != output_dir
            else relative_path
        ),
    )

    os.makedirs(os.path.dirname(output_file), exist_ok=True)

    with open(output_file, "w", encoding="utf-8") as f:
        for i, line in enumerate(lines):
            f.write(line)
            if i < len(lines) - 1:
                f.write("\n")  # No extra newline at end of file


def preprocess_java_files(
    input_dir: str = "", output_path: str = "output", max_workers: int = 8
):
    input_dir = os.path.abspath(input_dir or ".")
    output_path = (
        input_dir if output_path == "inplace" else os.path.abspath(output_path)
    )
    os.makedirs(output_path, exist_ok=True)

    java_files = [str(file) for file in Path(input_dir).rglob("*.java")]

    with ThreadPoolExecutor(max_workers=max_workers) as executor:
        futures = {
            executor.submit(
                process_single_file, filename, input_dir, output_path
            ): filename
            for filename in java_files
        }
        for future in tqdm.tqdm(
            as_completed(futures), total=len(futures), desc="Processing Java files"
        ):
            try:
                future.result()
            except Exception as e:
                print(f"Error processing {futures[future]}: {e}")


if __name__ == "__main__":
    preprocess_java_files(INPUT, OUTPUT)
