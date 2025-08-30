"""
This script separates AI-generated images into folders by model name.

It scans a given input directory recursively for PNG images whose filenames contain one of the specified AI model names (chatgpt, gemini, claude, deepseek).
For each matching image, it replicates the input folder structure under an output directory, placing each image in a subfolder named after the detected model.

Usage:
- Set 'input_directory' to the folder containing images.
- Set 'output_directory' to the desired destination folder.
- Run the script to copy and organize images by model.
"""

import os
import shutil


models = ["chatgpt", "gemini", "claude", "deepseek"]


def separate_ai_images(input_dir, output_dir):
    for root, dirs, files in os.walk(input_dir):
        rel_root = os.path.relpath(root, input_dir)
        for img in files:
            if img.lower().endswith(".png"):
                for model in models:
                    if model in img:
                        # Replicate the folder structure
                        model_dir = os.path.join(output_dir, model, rel_root)
                        os.makedirs(model_dir, exist_ok=True)
                        shutil.copy(
                            os.path.join(root, img), os.path.join(model_dir, img)
                        )


if __name__ == "__main__":
    input_directory = "Java_20perEx_img_ai_raw"
    output_directory = "."
    separate_ai_images(input_directory, output_directory)
