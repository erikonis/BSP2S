from pathlib import Path
import re, os, shutil
from concurrent.futures import ThreadPoolExecutor, as_completed

import tqdm

""" This module provides functions to remove the comments from a Java file.
    There are configurable Hyperparameters, allowing to choose the INPUT folder (i.e.
    folder, where the Java files are pooled. After running the main method, the code
    iterates through all the Java files, reads them as plain text and removes the comments.
    Then it saves in the specified OUTPUT folder the cleaned Java files named as before + "P"
    letter at the filename end (before extension), to flag the file as Preprocessed.
    E.g. ("SomeJavaFile.java" -> "SomeJavaFileP.java").
"""

INPUT = "AIvsSTD_std_raw"  # empty string means current directory
OUTPUT = "AIvsSTD_std_preproc"  # "inplace" to overwrite in input dir


def remove_java_comments(source_code: str) -> str:
    """
    Removes all single-line, multi-line, and Javadoc comments from a Java string.

    :param source_code: The Java code as a string.
    """

    lines = source_code.split("\n")
    in_block_comment = False
    result = []

    for line in lines:
        i = 0
        new_line = []
        while i < len(line):
            if not in_block_comment and line[i : i + 2] == "/*":
                in_block_comment = True
                i += 2
            elif in_block_comment and line[i : i + 2] == "*/":
                in_block_comment = False
                i += 2
            elif not in_block_comment and line[i : i + 2] == "//":
                break  # Ignore rest of line
            elif not in_block_comment:
                new_line.append(line[i])
                i += 1
            else:
                i += 1  # Skip chars inside block comment

        cleaned = "".join(new_line).rstrip()
        # Preserve blank lines, skip comment-only lines
        if cleaned or not line.strip():
            result.append(cleaned)
    return "\n".join(result)


def remove_extra_blank_lines(code: str) -> str:
    lines = code.split("\n")
    result = []
    blank_count = 0
    for line in lines:
        if not line.strip():
            blank_count += 1
            if blank_count <= 1:
                result.append("")
        else:
            blank_count = 0
            result.append(line)
    return "\n".join(result)


def remove_java_comments_and_limit_blank_lines(source_code: str) -> str:
    cleaned = remove_java_comments(source_code)
    return remove_extra_blank_lines(cleaned)


def process_single_file(filename: str, input_dir: str, output_dir: str):
    with open(filename, "r", encoding="utf-8") as file:
        java_code = file.read()

    cleaned_code = remove_java_comments_and_limit_blank_lines(java_code)

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
