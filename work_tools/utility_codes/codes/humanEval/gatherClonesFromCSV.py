import shutil
import pandas as pd
import os.path

"""
This file reads the CSV file that is a converted XML file obtained from NiCad6 report, specifying duplicate groups and the file names.
File then finds the duplicates, leaves a single sample and moves other files into the OUTPUT_DIR
"""

# HYPERPARAMETERS:
CSV_FILENAME = "file_class.csv"  # has to be path
INPUT_DIR = ".."
OUTPUT_DIR = "output"


class Constants:
    classID = "classid"
    file = "file"


def readCSV(filename: str):
    return pd.read_csv(filename)


def move_file(input: str, output: str):
    shutil.move(input, output)


def group_files(dataframe: pd.DataFrame) -> dict:
    dic = {}
    for idx, row in dataframe.iterrows():
        class_id = row[Constants.classID]
        file = os.path.basename(row[Constants.file])

        if not class_id in dic:
            dic[class_id] = []

        dic[class_id].append(file)
    return dic


def get_clone_files(dic: dict) -> list:

    lst = []

    for k in dic.keys():
        # lst.append(dic[k][0])
        lst.extend(dic[k][1:])

    return lst


def move_clone_files(input: str, output: str, lst: list):
    os.makedirs(output, exist_ok=True)

    for filename in lst:
        in_path = os.path.join(input, filename)
        out_path = os.path.join(output, filename)
        move_file(in_path, out_path)


def main():
    csv = readCSV(CSV_FILENAME)

    class_groups = group_files(csv)
    files = get_clone_files(class_groups)
    move_clone_files(INPUT_DIR, OUTPUT_DIR, files)


if __name__ == "__main__":
    main()
