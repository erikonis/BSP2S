import os, csv, shutil, random
from concurrent.futures import ThreadPoolExecutor

"""This code reads a CSV file containing exercise IDs. Csv file must contain a column named 'Exercise'.
   Then it checks the file names in the CWD, if the name contains good ID, it copies it into an output directory."""



OUTPUT_DIR = "output"
CSV_FILE = "good_upBound_ex_w32.csv"

def copy_file(src, dst):
    shutil.copy(src, dst)

def main():
    with open(CSV_FILE, 'r') as csvfile:
        reader = csv.DictReader(csvfile)
        os.makedirs(OUTPUT_DIR, exist_ok=True)
        tasks = []

        with ThreadPoolExecutor(max_workers=4) as executor:
            exercise_ids = set()

            for row in reader:
                exercise_ids.add(row['Exercise'])

            dir = "."
            files = [f for f in os.listdir(dir) if os.path.isfile(os.path.join(dir, f)) and f.endswith('java') and
                     f.split('_')[1] in exercise_ids]
            for filename in files:
                src = os.path.join(dir, filename)
                dst = os.path.join(OUTPUT_DIR, filename)
                tasks.append(executor.submit(copy_file, src, dst))

            for task in tasks:
                task.result()

if __name__ == "__main__":
    main()