import matplotlib.pyplot as plt
import os
import numpy as np

"""
This script analyzes the sizes of Java files in a given directory and generates a histogram of their sizes.
"""

# Hyperparameters
INPUT_DIR = "."
BINS = 150  # the number of bins in the histogram
THRESHOLD = 192  # in bytes, files larger than this are considered "good"
TITLE = "Distribution of Raw AI Java File Sizes"
LOGARITHMIC = False


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
    :return: (good_file_sizes, bad_file_sizes) in kB
    """

    good_file_sizes = []
    bad_file_sizes = []

    for file_path in file_paths:
        try:
            size = os.path.getsize(file_path)  # Get file size in bytes
            if size > threshold:
                good_file_sizes.append(size / 1000)  # Convert to kB
            else:
                bad_file_sizes.append(size / 1000)  # Convert to kB
        except Exception as e:
            print(f"Failed to read {file_path}: {e}")

    if not good_file_sizes and not bad_file_sizes:
        print("No files with 'good' or 'over' quality found.")
    return good_file_sizes, bad_file_sizes


def Main(
    good_sizes, bad_sizes, title, output_path="size_distribution.png", logarithmic=False
):

    file_sizes = good_sizes + bad_sizes

    if good_sizes or bad_sizes:
        # Create histogram

        threshold_kb = max(bad_sizes) if bad_sizes else THRESHOLD / 1000

        # Create histogram with two colors based on threshold
        plt.figure(figsize=(10, 6))

        # Create bins
        if logarithmic:
            output_path = output_path.split(".")[0] + "_logarithmic.png"
            title = "Logarithmic " + title

            min_size = max(min(file_sizes), 0.001)  # Avoid log(0)
            max_size = max(file_sizes)

            bins = np.logspace(np.log10(min_size), np.log10(max_size), BINS)
            n, bins, patches = plt.hist(file_sizes, bins=bins, edgecolor="black")
            plt.xscale("log")  # <-- This is key!
            plt.xticks([0.1, 1, 10, 100, 1000], ["0.1", "1", "10", "100", "1000"])
            # plt.ylim(0, 700)
        else:
            bins = BINS
            n, bins, patches = plt.hist(file_sizes, bins=bins, edgecolor="black")
            # plt.ylim(0, 8000)

        # Color the bars based on threshold
        for i in range(len(patches)):
            if bins[i] <= threshold_kb:
                patches[i].set_facecolor("lightcoral")
            else:
                patches[i].set_facecolor("skyblue")

        plt.xlabel("File Size (kB)")
        plt.ylabel("Number of Files")
        plt.title(title)
        plt.grid(True, alpha=0.3)
        # plt.legend()

        # Add statistics as text
        avg_size = sum(good_sizes) / len(good_sizes)
        max_size = max(good_sizes)
        min_size = min(good_sizes)

        stats_text = f"Total files: {len(file_sizes)}\n"
        stats_text += f"h>2 Average size: {avg_size:.2f} kB\n"
        stats_text += f"h>2 Max size: {max_size:.2f} kB\n"
        stats_text += f"h>2 Min size: {min_size:.2f} kB"

        plt.text(
            0.95,
            0.95,
            stats_text,
            transform=plt.gca().transAxes,
            verticalalignment="top",
            horizontalalignment="right",
            bbox=dict(boxstyle="round", facecolor="white", alpha=0.8),
        )

        # Save the plot
        plt.savefig(output_path, dpi=300, bbox_inches="tight")
        plt.close()

        print(f"Analysis complete. Plot saved in {output_path}.")
        print(stats_text)
    else:
        print("No PNG images found.")


if __name__ == "__main__":
    java_files = get_java_paths(INPUT_DIR)
    good, bad = analyze_java_files(java_files, THRESHOLD)
    Main(good, bad, title=TITLE, logarithmic=LOGARITHMIC)
