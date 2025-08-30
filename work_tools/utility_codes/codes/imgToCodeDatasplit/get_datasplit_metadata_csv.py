from pathlib import Path
import pandas as pd

""" Constructs a CSV file with columns: filename, dataset, origin, full_filename
    Filename is without extension and directories while dataset can be "train" or "test".

    Main purpose of the code is to obtain metadata from constructed training set, to track the corresponding Java files.
"""

DIRECTORY = "."


def find_java_files(directory: str):
    return [str(file) for file in Path(directory).rglob("*.png")]


def create_set_split_csv(directory: str):

    files = find_java_files(directory)
    columns = ["filename", "dataset", "origin", "full_filename"]
    data = [
        (
            Path(file).stem,
            "test" if "test" in file else "train",
            # Case check - determining the file origin
            (
                "chatgpt"
                if "chatgpt" in file
                else (
                    "gemini"
                    if "gemini" in file
                    else (
                        "deepseek"
                        if "deepseek" in file
                        else "claude" if "claude" in file else "human"
                    )
                )
            ),
            file,
        )
        for file in files
    ]

    df = pd.DataFrame(data, columns=columns)
    df.to_csv("set_split.csv", index=False)


if __name__ == "__main__":
    create_set_split_csv(DIRECTORY)
