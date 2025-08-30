import os
import pandas as pd
import json

"""
This file takes METADATA_CSV directory and checks the FILES_DIR directory. For each file found in the directory, it finds correponsind metadata of the file and saves
it in JSON format. This is all done to localize metadata for the files that are relevant.
"""

FILES_DIR = "."
METADATA_CSV = "Java_metadata.csv"

required_columns = [
    "s_id",
    "p_id",
    "u_id",
    "date",
    "language",
    "original_language",
    "filename_ext",
    "status",
    "cpu_time",
    "memory",
    "code_size",
]

metadata = pd.read_csv(METADATA_CSV, dtype=str)
all_meta = []

for filename in os.listdir(FILES_DIR):
    if os.path.isfile(filename) and filename != "Java_metadata.csv":
        s_id, ext = os.path.splitext(filename)
        row = metadata[metadata["s_id"] == s_id]
        if not row.empty:
            meta_dict = row.iloc[0][required_columns].to_dict()
            all_meta.append(meta_dict)

with open("all_metadata.json", "w", encoding="utf-8") as f:
    json.dump(all_meta, f, ensure_ascii=False, indent=2)
