import os
import csv
import random
import subprocess
import sys
import time
import numpy as np
from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt
import seaborn as sns
from tqdm import tqdm
from predict_image_dn121 import main as predict_dn121
from predict_image_rn50 import main as predict_rn50
from predict_image_vgg16 import main as predict_vgg16


MODEL_PATH = "models/best_model_dn121_preprocessed_noWhitespace.pth"
IMAGES_DIR = "test_sets_outside/images/preprocessed_noWhitespace/img_good"
NUMBER_OF_FILES = 1000  # Number of files to evaluate. If set higher than exist, then all are evaluated
SPECIAL_PREFIX = "outside_"  # "" if none

"""
usage in cmd:
> python evaluate_model.py <model_path.model.pth> <images_diretory> <number of files (opt.)>
"""


def main(model_path, number_of_files, images_direction=IMAGES_DIR):

    if "dn121" in model_path:
        model_name = "DenseNet121"
    elif "rn50" in model_path:
        model_name = "ResNet50"
    elif "vgg16" in model_path:
        model_name = "VGG16"

    output_dir = (
        SPECIAL_PREFIX
        + os.path.splitext(model_path)[0]
        + f"_data{images_direction.split("_")[-1]}"
    )

    os.makedirs(output_dir, exist_ok=True)

    output_csv = os.path.join(output_dir, "evaluation_results.csv")
    output_cm = os.path.join(output_dir, "Confusion_Matrix.png")
    output_time_stats = os.path.join(output_dir, "evaluation_time_stats.txt")
    output_failed = os.path.join(output_dir, "failed_predictions.txt")

    image_files = [
        f
        for f in os.listdir(images_direction)
        if f.lower().endswith((".png", ".jpg", ".jpeg", ".bmp"))
    ]
    sampled_files = random.sample(image_files, min(number_of_files, len(image_files)))

    y_true = []
    y_pred = []
    results = []
    eval_times = []
    failed = []

    start_total = time.time()

    for filename in tqdm(sampled_files, desc="Evaluating images"):
        image_path = os.path.join(images_direction, filename)
        true_label = 0 if "std" in filename or "hum" in filename else 1

        try:
            start = time.time()

            if "dn121" in model_path:
                pred, prob = predict_dn121(model_path, image_path)
            elif "rn50" in model_path:
                pred, prob = predict_rn50(model_path, image_path)
            elif "vgg16" in model_path:
                pred, prob = predict_vgg16(model_path, image_path)
            else:
                raise ValueError(f"Unknown model type in filename: {filename}")

            elapsed = time.time() - start
            y_true.append(true_label)
            y_pred.append(pred)
            results.append([filename, true_label, pred, prob, elapsed])
            eval_times.append(elapsed)
        except Exception as e:
            failed.append(f"{filename} | Error: {e}")

    total_eval_time = time.time() - start_total
    avg_time = np.mean(eval_times) if eval_times else 0
    min_time = np.min(eval_times) if eval_times else 0
    max_time = np.max(eval_times) if eval_times else 0

    with open(output_csv, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(
            ["filename", "true_label", "prediction", "probability", "eval_time_sec"]
        )
        writer.writerows(results)

    with open(output_time_stats, "w") as f:
        f.write(f"Total evaluation time (sec): {total_eval_time:.4f}\n")
        f.write(f"Average time per image (sec): {avg_time:.4f}\n")
        f.write(f"Min time per image (sec): {min_time:.4f}\n")
        f.write(f"Max time per image (sec): {max_time:.4f}\n")

    if failed:
        with open(output_failed, "w") as f:
            for line in failed:
                f.write(line + "\n")

    if y_true and y_pred:
        cm = confusion_matrix(y_true, y_pred)
        plt.figure(figsize=(6, 6))
        sns.heatmap(
            cm,
            annot=True,
            fmt="d",
            cmap="Blues",
            xticklabels=["Human", "AI"],
            yticklabels=["Human", "AI"],
            annot_kws={"size": 36},
        )
        plt.xlabel("Predicted", color="#1f77b4", labelpad=-45, fontsize=18)
        plt.ylabel("True", color="#1f77b4", labelpad=-45, fontsize=18)
        plt.tick_params(axis="both", which="major", pad=25, labelsize=24)
        accuracy = (np.array(y_true) == np.array(y_pred)).sum() / len(y_true) * 100
        formatted_accuracy = (
            f"{accuracy:.1f}" if accuracy % 1 != 0 else f"{int(accuracy)}"
        )
        plt.title(f"{model_name}\nAccuracy {formatted_accuracy}%", pad=20, fontsize=24)
        plt.tight_layout()
        plt.savefig(output_cm)
        plt.close()


if __name__ == "__main__":
    model_path = MODEL_PATH
    images_direct = IMAGES_DIR
    number_of_files = NUMBER_OF_FILES
    main(model_path, number_of_files, images_direct)
