import matplotlib.pyplot as plt
import os
import numpy as np

"""
This script analyzes the sizes of Java files in a given directory and generates a histogram of their sizes.
"""

# Hyperparameters
INPUT_DIR = "Java_20perEx_codes_ai_raw"
OUTPUT = "width_distribution_ai_raw.png"
TITLE = "Distribution of Raw AI Image widths"

BINS = ["32", "64", "128", "256", "384", "512"]
THRESHOLD = 192  # in bytes, files larger than this are considered "good"
LOGARITHMIC = False

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


def resolve_width(size: int):
    for key, bounds in boundaries.items():
        if bounds["low"] <= size < bounds["high"]:
            return str(key)
    return None


def get_java_paths(path):
    java_paths = []
    for filename in os.listdir(path):
        if filename.endswith(".java"):
            full_path = os.path.join(path, filename)
            java_paths.append(full_path)
    return java_paths


def analyze_java_files(file_paths, threshold=192):
    """
    Analyze the sizes of Java files based on their quality and return lists of good and bad file sizes.
    :param file_paths: a list of java file paths
    :param threshold: a number in bytes. File with size greater than threshold is considered as good. Otherwise false.
    :return: (good_file_widths, bad_file_widths) in px
    """

    good_file_widths = []
    bad_file_widths = []

    for file_path in file_paths:
        try:
            size = os.path.getsize(file_path)  # Get file size in bytes
            width = resolve_width(size)
            if width is not None:
                if size > threshold:
                    good_file_widths.append(width)
                else:
                    bad_file_widths.append(width)
        except Exception as e:
            print(f"Failed to read {file_path}: {e}")

    if not good_file_widths and not bad_file_widths:
        print("No files with 'good' or 'over' quality found.")
    return good_file_widths, bad_file_widths


def Main(
    good_sizes,
    bad_sizes,
    title,
    output_path=OUTPUT,
    logarithmic=False,
):

    # Prepare bins for widths 0 to 512
    max_width = 512
    bins = BINS

    # Count good and bad files per width
    good_counts = np.zeros(len(bins), dtype=int)
    bad_counts = np.zeros(len(bins), dtype=int)
    for w in good_sizes:
        if w in bins:
            idx = bins.index(w)
            good_counts[idx] += 1
    for w in bad_sizes:
        if w in bins:
            idx = bins.index(w)
            bad_counts[idx] += 1

    if np.any(good_counts):
        plt.figure(figsize=(12, 6))
        # Categorical bar chart: each bin is a category

        x = list(BINS)

        bar_width = 1
        plt.bar(
            x,
            bad_counts,
            color="lightcoral",
            edgecolor="black",
            label="Bad files",
            width=bar_width,
        )
        plt.bar(
            x,
            good_counts,
            bottom=bad_counts,
            color="skyblue",
            edgecolor="black",
            label="Good files",
            width=bar_width,
        )

        plt.xlabel("Width")
        plt.ylabel("Number of Files")
        plt.title(title)
        plt.grid(True, alpha=0.3)
        plt.legend()
        plt.ylim([0, int((good_counts + bad_counts).max() * 1.1)])

        # Add statistics as text (for good files only)
        # For statistics, convert bin labels to int
        int_bins = [int(b) for b in bins]
        good_widths = np.repeat(int_bins, good_counts)
        if good_widths.size > 0:
            avg_width = np.average(good_widths)
            max_width_val = np.max(good_widths)
            min_width_val = np.min(good_widths)
        else:
            avg_width = max_width_val = min_width_val = 0

        stats_text = f"Total files: {int(np.sum(good_counts))}\n"
        stats_text += f"Average width: {avg_width:.2f}\n"
        stats_text += f"Max width: {max_width_val}\n"
        stats_text += f"Min width: {min_width_val}"

        plt.text(
            0.99,
            0.99,
            stats_text,
            transform=plt.gca().transAxes,
            verticalalignment="top",
            horizontalalignment="right",
            bbox=dict(boxstyle="round", facecolor="white", alpha=0.8),
        )

        plt.savefig(output_path, dpi=300, bbox_inches="tight")
        plt.close()

        print(f"Analysis complete. Plot saved in {output_path}.")
        print(stats_text)
    else:
        print("No files found.")


if __name__ == "__main__":
    java_files = get_java_paths(INPUT_DIR)
    good, bad = analyze_java_files(java_files, THRESHOLD)
    Main(good, bad, title=TITLE, logarithmic=LOGARITHMIC)
