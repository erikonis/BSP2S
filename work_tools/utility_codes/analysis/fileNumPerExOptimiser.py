import pandas as pd
import matplotlib.pyplot as plt

# Constants (user can adjust)
CSV_PATH = "Java_metadata.csv"  # Path to your CSV file
MIN_SIZE = 192  # Minimum code_size (inclusive)
MAX_SIZE = 100000  # Maximum code_size (inclusive)
MAX_X = 1000  # Maximum x value to consider

"""
This file plots a graph showing the optimal number of number of minimum file number in an exercise sheet threshold.
X is the minimum number of files per exercise sheet (x)
Y is the resulting total number of files if we considered that setting defined by formula y = x*N, where N - number of exercise sheets
with at least x solutions.

Logically, the bigger the x, the smaller N.

"""


def main(csv_path, min_size, max_size, max_x):
    # Read CSV
    df = pd.read_csv(csv_path)
    # Filter for Accepted status and code_size in range
    df = df[
        (df["status"] == "Accepted")
        & (df["code_size"] >= min_size)
        & (df["code_size"] <= max_size)
    ]
    # Group by p_id and count files per exercise
    p_id_counts = df.groupby("p_id").size()
    # For each x, count how many p_ids have at least x files
    xs = list(range(1, max_x + 1))
    ns = [sum(p_id_counts >= x) for x in xs]
    ys = [n * x for n, x in zip(ns, xs)]
    # Find optimal x (max y)
    optimal_x = xs[ys.index(max(ys))]
    optimal_y = max(ys)

    # Plot both graphs in parallel
    fig, axs = plt.subplots(1, 2, figsize=(18, 7))

    # N*x plot
    axs[0].plot(xs, ys, marker="o")
    axs[0].scatter(
        [optimal_x],
        [optimal_y],
        color="red",
        label=f"Optimal x={optimal_x} (y={optimal_y})",
    )
    axs[0].axvline(optimal_x, color="red", linestyle="--", label="Optimal x")
    axs[0].set_xlabel("Minimum files per exercise (x)")
    axs[0].set_ylabel("N*x (N = #exercises with ≥x files)")
    axs[0].set_title("Optimal Number of Files per Exercise")
    axs[0].legend()
    axs[0].grid(True)

    # N plot
    axs[1].plot(xs, ns, marker="o", color="green")
    axs[1].scatter(
        [optimal_x],
        [ns[optimal_x - 1]],
        color="red",
        label=f"Optimal x={optimal_x} (N={ns[optimal_x-1]})",
    )
    axs[1].axvline(optimal_x, color="red", linestyle="--", label="Optimal x")
    axs[1].set_xlabel("Minimum files per exercise (x)")
    axs[1].set_ylabel("N (Number of exercises with ≥x files)")
    axs[1].set_title("Number of Exercises with at Least x Files")
    axs[1].legend()
    axs[1].grid(True)

    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    main(CSV_PATH, MIN_SIZE, MAX_SIZE, MAX_X)
