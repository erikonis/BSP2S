import matplotlib.pyplot as plt
import os
import numpy as np

"""
This script analyzes the sizes of Java files in a given directory and generates a histogram of their sizes.
"""

# Hyperparameters
INPUT_HUMANS_DIR = "Java_20perEx_codes_hum_raw"
INPUT_AI_DIR = "Java_20perEx_codes_ai_raw"
BINS = 80  # the number of bins in the histogram
THRESHOLD = 192  # in bytes, files larger than this are considered "good"
TITLE = 'AI vs Human Raw Java File Sizes Distribution'
LOGARITHMIC = False


def get_java_paths(path):
    java_paths = []
    for filename in os.listdir(path):
        if filename.endswith(".java"):
            full_path = os.path.join(path, filename)
            java_paths.append(full_path)
    return java_paths


def analyze_java_files(file_paths):
    """
    Analyze the sizes of Java files based on their quality and return lists of good and bad file sizes.
    :param file_paths: a list of java file paths
    :param threshold: a number in bytes. File with size greater than threshold is considered as good. Otherwise false.
    :return: file_sizes (list) in kB
    """

    file_sizes = []

    for file_path in file_paths:
        try:
            size = os.path.getsize(file_path)  # Get file size in bytes
            file_sizes.append(size / 1000)  # Convert to kB
        except Exception as e:
            print(f"Failed to read {file_path}: {e}")

    return file_sizes


def Main(ai_sizes, human_sizes, title, output_path="ai_vs_human_hist_log.png"):
    plt.figure(figsize=(10, 6))

    # Use shared log-scale bins for fair comparison
    all_sizes = ai_sizes + human_sizes
    min_size = max(min(all_sizes), 0.001)
    max_size = max(all_sizes)

    # cutoff = 50  # kB
    # bins_below = np.logspace(np.log10(min_size), np.log10(cutoff), 80)
    # bins = np.append(bins_below, [max_size * 1.1])

    bins = np.logspace(np.log10(min_size), np.log10(max_size), BINS)

    # Plot both
    plt.hist(ai_sizes, bins=bins, alpha=0.5, label='AI', edgecolor='black', color='red')
    plt.hist(human_sizes, bins=bins, alpha=0.5, label='Human', edgecolor='black', color='skyblue')

    # ----------------
    ai_mean = np.mean(ai_sizes)
    human_mean = np.mean(human_sizes)
    plt.axvline(ai_mean, color='red', linestyle='--', label=f'AI Mean ({ai_mean:.2f} kB)')
    plt.axvline(human_mean, color='blue', linestyle='--', label=f'Human Mean ({human_mean:.2f} kB)')

    # --------------
    # plt.hist([ai_sizes, human_sizes], bins=bins, stacked=True,label=['AI', 'Human'], color=['skyblue', 'salmon'], edgecolor='black')

    # --------------
    max_x = 220.6

    if bins[-1] < max_x:
        bins = np.append(bins, max_x)

    plt.xscale('log')
    plt.ylim(0, 1650)  # autoscale y
    plt.xlim(0.2, max_x)

    # Labels and legend
    plt.xlabel('File Size (kB)')
    plt.ylabel('Number of Files')
    plt.title(title)
    plt.legend()
    plt.grid(True, which='both', alpha=0.3)

    plt.xticks(bins[::10], [f"{x:.1f}" for x in bins[::10]])  # optional: fewer xticks
    plt.tight_layout()
    plt.savefig(output_path, dpi=300)


if __name__ == "__main__":
    java_files = get_java_paths(INPUT_HUMANS_DIR)
    human = analyze_java_files(java_files)

    java_files = get_java_paths(INPUT_AI_DIR)
    ai = analyze_java_files(java_files)

    Main(ai, human, title=TITLE)
