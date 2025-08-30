import os, csv, shutil, random
from concurrent.futures import ThreadPoolExecutor

"""This code reads a CSV file containing exercise IDs. Csv file must contain a column named 'Exercise'.
   Then the the code iterates through the Exercise ID's as given by CSV and for each ID enters in the
   cwd directory named after the ID, then enter the 'Accepted' directory within and randomly copies
   N files (or all if N is None). It then puts all copied files in a single directory named OUTPUT_DIR."""


OUTPUT_DIR = "gathered_accepted_files"
CSV_FILE = "good_ex_w32.csv"
N = 5  # Set to an integer to pick N random files per folder, or None to copy all
min_size = 192  # Bytes. Exclusive boundary
max_size = 13385  # Bytes. Inclusive boundary


def copy_file(src, dst):
    shutil.copy(src, dst)


def main():
    with open(CSV_FILE, "r") as csvfile:
        reader = csv.DictReader(csvfile)
        os.makedirs(OUTPUT_DIR, exist_ok=True)
        tasks = []

        with ThreadPoolExecutor() as executor:
            for row in reader:
                exercise_id = row["Exercise"]
                dir = os.path.join(exercise_id, "Accepted")
                if not os.path.isdir(dir):
                    continue
                files = [
                    f
                    for f in os.listdir(dir)
                    if os.path.isfile(os.path.join(dir, f))
                    and min_size < os.path.getsize(os.path.join(dir, f)) <= max_size
                ]
                if N is not None:
                    files = random.sample(files, min(N, len(files)))
                for filename in files:
                    src = os.path.join(dir, filename)
                    dst = os.path.join(OUTPUT_DIR, filename)
                    tasks.append(executor.submit(copy_file, src, dst))
            for task in tasks:
                task.result()


if __name__ == "__main__":
    main()
