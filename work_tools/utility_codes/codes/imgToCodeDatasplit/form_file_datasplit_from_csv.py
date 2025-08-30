from concurrent.futures import ThreadPoolExecutor, as_completed
import os
import shutil
import pandas as pd
from tqdm import tqdm

"""
This file takes a special set_split.csv, formated with get_datasplit_metadata.csv.
Then it creates a copy of image based dataset split prepared for training with the actual corresponding java files.
"""


CSV_PATH = "set_split.csv"
OUTPUT_DIR = "output"
INPUT_DIR_AI = ""
INPUT_DIR_HUM = ""


def copy_file(in_path, out_path):

    os.makedirs(os.path.dirname(out_path), exist_ok=True)
    shutil.copy2(in_path, out_path)


def read_csv(csv_path: str):
    df = pd.read_csv(csv_path)
    return df


def main():
    csv_data = read_csv(CSV_PATH)

    works = []

    for index, row in csv_data.iterrows():

        if row["origin"] == "human":
            file_path = os.path.join(INPUT_DIR_HUM, row["filename"] + ".java")
            flag = 0
        else:
            file_path = os.path.join(INPUT_DIR_AI, row["filename"] + ".java")
            flag = 1

        out_path = os.path.join(OUTPUT_DIR, row["dataset"], str(flag))
        os.makedirs(out_path, exist_ok=True)
        out_path = os.path.join(out_path, row["filename"] + ".java")

        works.append((file_path, out_path))

    with ThreadPoolExecutor() as executor:
        futures = [executor.submit(copy_file, work[0], work[1]) for work in works]
        for future in tqdm(
            as_completed(futures), total=len(futures), desc="Copying files"
        ):
            future.result()


if __name__ == "__main__":
    main()
