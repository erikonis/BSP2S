import os
import csv
import sys
from datetime import datetime

csv.field_size_limit(sys.maxsize)

"""
This file generates general statistics about exercise sheets in a dataset.
It counts the number of available and accepted exercise files for each sheet,
based on a specified threshold, and outputs summary statistics to a CSV and TXT file.
The script is useful for identifying sheets with insufficient exercises and
for analyzing the distribution of exercises across the dataset.
"""

THRESHOLD = 20
UNAVAILABLE_CSV_OUTPUT = "unavailable_exercises_updated.csv"
STATS_TXT = "exercise_statistics_updated.txt"
START_IDX = 0
END_IDX = 4052  # inclusive

HEIGHT = 2
WIDTHS = [32]
BOUNDARY_COLUMN_SIZE = HEIGHT * 3
SHEET_DIR = "../../../CodeNet Questions"
SAVE_CSV = True


def count_files_in_accepted(folder):
    accepted_path = os.path.join(folder, "Accepted")
    if not os.path.isdir(accepted_path):
        return 0
    return sum(
        1
        for f in os.listdir(accepted_path)
        if os.path.isfile(os.path.join(accepted_path, f))
    )


def count_all_files_in_folder(folder):
    if not os.path.isdir(folder):
        return 0
    total = 0
    for subfolder in os.listdir(folder):
        subfolder_path = os.path.join(folder, subfolder)
        if os.path.isdir(subfolder_path):
            total += sum(
                1
                for f in os.listdir(subfolder_path)
                if os.path.isfile(os.path.join(subfolder_path, f))
            )
    return total


def count_large_files_in_accepted(folder, min_size, max_size: int = 10000000):
    accepted_path = os.path.join(folder, "Accepted")
    if not os.path.isdir(accepted_path):
        return (0, None)

    sums = 0
    min_file_size = None

    for f in os.listdir(accepted_path):
        size = (
            os.path.getsize(os.path.join(accepted_path, f))
            if os.path.isfile(os.path.join(accepted_path, f))
            else 0
        )
        if min_size < size <= max_size:
            sums += 1
            min_file_size = (
                size if min_file_size is None or size < min_file_size else min_file_size
            )

    return (sums, min_file_size)


def main():
    current_dir = os.getcwd()
    sheet_dir = SHEET_DIR
    existing_sheets = set(
        f[:-5]
        for f in os.listdir(sheet_dir)
        if f.startswith("p") and f.endswith(".html")
    )
    all_exercises = [f"p{str(i).zfill(5)}" for i in range(START_IDX, END_IDX + 1)]

    # General stats
    exercises_has_sheet_no_solution = []
    exercises_has_sheet_has_solution = []
    exercises_no_sheet_no_solution = []
    exercises_no_sheet_has_solution = []

    files_has_sheet_has_solution = 0
    files_has_sheet_no_solution = 0
    files_has_sheet_total_accepted = 0
    files_has_sheet_total = 0

    files_no_sheet_has_solution = 0
    files_no_sheet_no_solution = 0
    files_no_sheet_total_accepted = 0
    files_no_sheet_total = 0

    total_files = 0
    total_accepted_files = 0
    total_unavailable_files = 0
    total_available_files = 0

    accepted_ids = []

    stats = []

    for exercise in all_exercises:
        folder_path = os.path.join(current_dir, exercise)
        has_sheet = exercise in existing_sheets

        if os.path.isdir(folder_path):
            accepted_files = count_files_in_accepted(folder_path)
            all_files = count_all_files_in_folder(folder_path)
        else:
            accepted_files = 0
            all_files = 0

        stats.append((exercise, accepted_files, has_sheet))

        total_files += all_files
        total_accepted_files += accepted_files

        if has_sheet and accepted_files >= THRESHOLD:
            exercises_has_sheet_has_solution.append((exercise, accepted_files))
            accepted_ids.append(exercise)
            total_available_files += accepted_files
            files_has_sheet_has_solution += accepted_files
            files_has_sheet_total += all_files
            files_has_sheet_total_accepted += accepted_files

        if has_sheet and accepted_files < THRESHOLD:
            exercises_has_sheet_no_solution.append((exercise, accepted_files))
            total_unavailable_files += accepted_files
            files_has_sheet_no_solution += accepted_files
            files_has_sheet_total += all_files
            files_has_sheet_total_accepted += accepted_files

        if not has_sheet and accepted_files >= THRESHOLD:
            exercises_no_sheet_has_solution.append((exercise, accepted_files))
            total_unavailable_files += accepted_files
            files_no_sheet_has_solution += accepted_files
            files_no_sheet_total += all_files
            files_no_sheet_total_accepted += accepted_files

        if not has_sheet and accepted_files < THRESHOLD:
            exercises_no_sheet_no_solution.append((exercise, accepted_files))
            total_unavailable_files += accepted_files
            files_no_sheet_no_solution += accepted_files
            files_no_sheet_total += all_files
            files_no_sheet_total_accepted += accepted_files

    # Write unavailable exercises CSV
    if SAVE_CSV:
        with open(UNAVAILABLE_CSV_OUTPUT, "w", newline="", encoding="utf-8") as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow(["Exercise", "NumSolutions"])
            writer.writerows(exercises_has_sheet_no_solution)
            writer.writerows(exercises_no_sheet_has_solution)
            writer.writerows(exercises_no_sheet_no_solution)

    stat_lines = [
        "=== Exercise Solutions Statistics ===",
        f"Run at: {datetime.now()}\n",
        f"THRESHOLD: {THRESHOLD}\n",
        f"=== Exercise Sheets Statistics ===",
        f"Total exercises: {len(all_exercises)}",
        f"---------------------------------------------------",
        f"Exercises with existing sheet: {len(existing_sheets)}",
        f"Exercises with existing sheet and enough solutions: {len(exercises_has_sheet_has_solution)}",
        f"Difference (dropped): {len(existing_sheets)-len(exercises_has_sheet_has_solution)}",
        f"---------------------------------------------------",
        f"Exercises without sheet: {len(exercises_no_sheet_no_solution)+len(exercises_no_sheet_has_solution)}",
        f"Exercises without sheet with enough solutions: {len(exercises_no_sheet_has_solution)}\n",
        f"=== Files Statistics ===",
        f"Total files: {total_files}",
        f"Total accepted files: {total_accepted_files}",
        f"Total not accepted files: {total_files - total_accepted_files}",
        f"Accepted, but unavailable: {total_unavailable_files}\n",
        f"=== Files Statistics for Exercises with Sheets ===",
        f"Total files: {files_has_sheet_total}",
        f"Total accepted files: {files_has_sheet_total_accepted}",
        f"Total accepted files, with enough solutions per exercise: {files_has_sheet_has_solution}",
        f"Total accepted files, with less than {THRESHOLD} solutions per exercise: {files_has_sheet_no_solution}\n",
        f"=== Files Statistics for Exercises without Sheets ===",
        f"Total files: {files_no_sheet_total}",
        f"Total accepted files: {files_no_sheet_total_accepted}",
        f"Total accepted files, with enough solutions per exercise: {files_no_sheet_has_solution}",
        f"Total accepted files, with less than {THRESHOLD} solutions per exercise: {files_no_sheet_no_solution}\n",
    ]

    low_solution = [(row[0], row[1]) for row in stats if row[1] < THRESHOLD]
    if low_solution:
        stat_lines.append("Examples of low-solution exercises:")
        for folder, num in low_solution[:5]:
            stat_lines.append(f"  {folder}: {num} solutions")

    if exercises_no_sheet_has_solution or exercises_no_sheet_no_solution:
        stat_lines.append("Exercises with no sheet:")
        i = 0
        string = ""
        for folder, num in (
            exercises_no_sheet_has_solution + exercises_no_sheet_no_solution
        ):
            i += 1
            string += f"  {folder},"
            if i == 10:
                stat_lines.append(string)
                string = ""
                i = 0
        if i != 0:
            stat_lines.append(string)

    if SAVE_CSV:
        stat_lines.append(f"CSV output written to: {UNAVAILABLE_CSV_OUTPUT}")

    # --- Code-size boundary stats ---
    stat_lines.append(
        "\n===================== BOUNDARIES BASED STATS ========================"
    )
    for w in WIDTHS:
        min_size = w * BOUNDARY_COLUMN_SIZE  # in bytes
        max_size = 13385  # in bytes
        total_files = 0
        min_files = 10000
        good_ids = []
        bad_ids = []
        min_file_size = None

        for exercise in accepted_ids:
            folder_path = os.path.join(current_dir, exercise)
            num_large, size = count_large_files_in_accepted(
                folder_path, min_size, max_size
            )

            if num_large >= THRESHOLD:
                total_files += num_large
                min_files = num_large if num_large < min_files else min_files
                min_file_size = (
                    size
                    if min_file_size is None or size < min_file_size
                    else min_file_size
                )

                good_ids.append((exercise, num_large))
            else:
                bad_ids.append((exercise, num_large))
        stat_lines.append(f"\n------------------- WIDTH : {w} --------------------")
        stat_lines.append(
            f"Files are considered sufficiently large if they are in ({min_size}, {max_size}] bytes range."
        )
        stat_lines.append(f"Exercises with sufficient large solutions: {len(good_ids)}")
        stat_lines.append(f"Total large solutions: {total_files}")
        stat_lines.append(
            f"Smallest number of files in a sufficient exercise: {min_files}"
        )
        stat_lines.append(
            f"Smallest file size in a sufficient exercise: {min_file_size} bytes"
        )
        stat_lines.append(
            f"Average num of files per exercise: {total_files / len(good_ids) if good_ids else 0:.2f}"
        )
        stat_lines.append(f"----------------------------------------------------")

        if SAVE_CSV:
            with open(
                f"good_ex_w{w}.csv", "w", encoding="utf-8", newline=""
            ) as csvfile:
                writer = csv.writer(csvfile)
                writer.writerow(["Exercise", "NumLargeSolutions"])
                writer.writerows(good_ids)

        if SAVE_CSV:
            with open(
                f"unavailable_ex_w{w}.csv", "w", encoding="utf-8", newline=""
            ) as csvfile:
                writer = csv.writer(csvfile)
                writer.writerow(["Exercise", "NumLargeSolutions"])
                writer.writerows(bad_ids)

    stats_text = "\n".join(stat_lines)
    print(stats_text)
    with open(STATS_TXT, "w", encoding="utf-8") as f:
        f.write(stats_text)


if __name__ == "__main__":
    main()
