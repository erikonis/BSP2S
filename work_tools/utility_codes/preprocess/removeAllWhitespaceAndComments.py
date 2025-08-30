"""
This script removes all comments and whitespace from Java source files in a directory tree.

It recursively finds all .java files in the specified root directory, strips out block and line comments, and deletes all whitespace characters. The cleaned files are saved to an output directory with modified filenames.

Usage:
- Set OUTPUT to the desired output directory.
- Run the script to preprocess all Java files for comment and whitespace removal.
"""

import os
import re
from concurrent.futures import ThreadPoolExecutor
from tqdm import tqdm

OUTPUT = "output"

def remove_java_comments(source_code: str) -> str:
    source_code = re.sub(r'/\*.*?\*/', '', source_code, flags=re.DOTALL)
    source_code = re.sub(r'//.*', '', source_code)
    return source_code

def remove_all_whitespace(code: str) -> str:
    return re.sub(r'\s+', '', code)

def process_single_file(filepath: str, output_dir: str):
    with open(filepath, 'r', encoding='utf-8', errors='replace') as file:
        java_code = file.read()
    code_no_comments = remove_java_comments(java_code)
    code_no_whitespace = remove_all_whitespace(code_no_comments)
    filename = os.path.basename(filepath)
    new_filename = filename.replace('.java', 'P.java')
    output_path = os.path.join(output_dir, new_filename)
    with open(output_path, 'w', encoding='utf-8') as file:
        file.write(code_no_whitespace)

def get_all_java_files(root_dir: str):
    java_files = []
    for dirpath, _, filenames in os.walk(root_dir):
        for filename in filenames:
            if filename.endswith('.java'):
                java_files.append(os.path.join(dirpath, filename))
    return java_files

def preprocess_java_files(root_dir: str = ".", output_dir: str = OUTPUT):
    os.makedirs(output_dir, exist_ok=True)
    java_files = get_all_java_files(root_dir)
    with ThreadPoolExecutor() as executor:
        list(tqdm(
            executor.map(lambda f: process_single_file(f, output_dir), java_files),
            total=len(java_files),
            desc="Processing Java files"
        ))

if __name__ == "__main__":
    preprocess_java_files(".", OUTPUT)