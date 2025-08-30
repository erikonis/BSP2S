from pathlib import Path

import torch
import time
import os
import numpy as np
from PIL import Image

import torch.nn as nn
from torchvision.models import densenet121, vgg16, resnet50
from torchvision import transforms

"""
This script evaluates time performance of a given model. It loads the specified model in the PATH_TO_MODEL (possible models are rn50, dn121 and vgg16),
script expects to find model acronym in the path. Then script evalutes each sample in the SAMPLES_DIR with the model and outputs a text file,
specifying the times it took for the model to evaluate.
"""

SAMPLES_DIR = "samples"
PATH_TO_MODEL = ""

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


def initialize_device():
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    print("Using device:", device)
    return device


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


def preprocess_file(name: str, input_dir: str, output_dir: str):
    path = os.path.join(input_dir, name)
    with open(path, "r", encoding="utf-8") as file:
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

    output_file = os.path.join(output_dir, name.replace(".java", "P.java"))

    # with open(output_file, 'w', encoding='utf-8') as file:
    #     file.write(cleaned_code)

    with open(output_file, "w", encoding="utf-8") as f:
        for i, line in enumerate(lines):
            f.write(line)
            if i < len(lines) - 1:
                f.write("\n")  # No extra newline at end of file


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
    return image


def find_java(input_dir):
    return [
        str(file) for file in Path(input_dir).glob("*.java")
    ]  # replace .glob("*.java") to .rglob("*.java") to iterate through all subdirs.


def single_code_to_image(input_dir, filename, output_dir):
    size = os.path.getsize(os.path.join(input_dir, filename))

    width = 32
    for key, value in boundaries.items():
        if value["low"] <= size < value["high"]:
            width = key
            break

    img = code_to_image(filename, width=width)

    output = os.path.join(output_dir, os.path.splitext(filename)[0] + ".png")
    img.save(output)
    return output


def load_model_dn121(model_path, device):
    model = densenet121(weights=None)  # Do not use pretrained weights
    model.classifier = nn.Linear(model.classifier.in_features, 1)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.eval()
    return model.to(device)


def load_model_rn50(model_path, device):
    model = resnet50(weights=None)  # Do not use pretrained weights
    model.fc = nn.Linear(model.fc.in_features, 1)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.eval()
    return model.to(device)


def load_model_vgg16(model_path, device):
    model = vgg16(weights=None)
    model.classifier[-1] = nn.Linear(model.classifier[-1].in_features, 1)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.eval()
    return model.to(device)


def preprocess_image(image_path):
    transform = transforms.Compose(
        [
            transforms.Resize((224, 224)),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
        ]
    )
    image = Image.open(image_path).convert("RGB")
    return transform(image).unsqueeze(0)


def predict_image(model, device, input_tensor):
    with torch.no_grad():
        output = model(input_tensor)
        prob = torch.sigmoid(output).item()
        pred = int(prob > 0.5)
    return pred, prob


def evaluate_timing(input_dir: str, java_file: str, path_to_model: str):
    os.makedirs("temp", exist_ok=True)
    filename = os.path.basename(java_file)

    start_time = time.time()
    preprocess_file(filename, input_dir, "temp")
    preprocess_duration = time.time() - start_time

    start_time = time.time()
    img_path = single_code_to_image("temp", filename, "temp")
    img_tensor = preprocess_image(img_path)
    rescale_image_duration = time.time() - start_time

    start_time = time.time()
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    if "dn121" in path_to_model:
        model = load_model_dn121(path_to_model, device)
    elif "rn50" in path_to_model:
        model = load_model_rn50(path_to_model, device)
    elif "vgg16" in path_to_model:
        model = load_model_vgg16(path_to_model, device)
    else:
        raise ValueError("Unsupported model type in path: {}".format(path_to_model))
    load_model_duration = time.time() - start_time

    start_time = time.time()
    pred, prob = predict_image(model, device, img_tensor.to(device))
    prediction_duration = time.time() - start_time

    return {
        "preprocess_duration": preprocess_duration,
        "rescale_image_duration": rescale_image_duration,
        "load_model_duration": load_model_duration,
        "prediction_duration": prediction_duration,
        "total_duration": preprocess_duration
        + rescale_image_duration
        + load_model_duration
        + prediction_duration,
    }


def process_times(times: list, PATH_TO_MODEL: str):
    if "dn121" in PATH_TO_MODEL:
        model_name = "DenseNet121"
    elif "rn50" in PATH_TO_MODEL:
        model_name = "ResNet50"
    elif "vgg16" in PATH_TO_MODEL:
        model_name = "VGG16"
    else:
        raise ValueError("Unsupported model type in path: {}".format(PATH_TO_MODEL))

    preprocess_durations = [time["preprocess_duration"] for time in times]
    rescale_image_durations = [time["rescale_image_duration"] for time in times]
    load_model_durations = [time["load_model_duration"] for time in times]
    prediction_durations = [time["prediction_duration"] for time in times]
    total_durations = [time["total_duration"] for time in times]

    with open(f"timing_stats_{model_name}.txt", "w") as file:
        file.write(f"TIMING STATISTICS FOR {model_name}:\n")
        file.write("=== PREPROCESS ===\n")
        file.write("Mean: {:.4f} s\n".format(np.mean(preprocess_durations)))
        file.write("Worst: {:.4f} s\n".format(np.max(preprocess_durations)))
        file.write("Best: {:.4f} s\n".format(np.min(preprocess_durations)))

        file.write("=== RESCALE IMAGE ===\n")
        file.write("Mean: {:.4f} s\n".format(np.mean(rescale_image_durations)))
        file.write("Worst: {:.4f} s\n".format(np.max(rescale_image_durations)))
        file.write("Best: {:.4f} s\n".format(np.min(rescale_image_durations)))

        file.write("=== LOAD MODEL ===\n")
        file.write("Mean: {:.4f} s\n".format(np.mean(load_model_durations)))
        file.write("Worst: {:.4f} s\n".format(np.max(load_model_durations)))
        file.write("Best: {:.4f} s\n".format(np.min(load_model_durations)))

        file.write("=== PREDICTION ===\n")
        file.write("Mean: {:.4f} s\n".format(np.mean(prediction_durations)))
        file.write("Worst: {:.4f} s\n".format(np.max(prediction_durations)))
        file.write("Best: {:.4f} s\n".format(np.min(prediction_durations)))

        file.write("=== TOTAL ===\n")
        file.write("Mean: {:.4f} s\n".format(np.mean(total_durations)))
        file.write("Worst: {:.4f} s\n".format(np.max(total_durations)))
        file.write("Best: {:.4f} s\n".format(np.min(total_durations)))


def main():
    files = find_java(SAMPLES_DIR)

    times = []

    for file in files:
        times.append(evaluate_timing(SAMPLES_DIR, file, PATH_TO_MODEL))

    process_times(times)
