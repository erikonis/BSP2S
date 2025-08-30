import os
import hashlib
from collections import defaultdict

"""
This file finds the exactly same file duplicates using the hashing function.
"""


def hash_file(filepath):
    """Returns SHA-256 hash of a file."""
    hasher = hashlib.sha256()
    with open(filepath, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            hasher.update(chunk)
    return hasher.hexdigest()


def find_duplicates(root_dir):
    hash_map = defaultdict(list)
    total_files = 0
    for dirpath, _, filenames in os.walk(root_dir):
        for fname in filenames:
            if fname.endswith(".java"):
                total_files += 1
                full_path = os.path.join(dirpath, fname)
                file_hash = hash_file(full_path)
                hash_map[file_hash].append(full_path)
    # Filter to only show actual duplicates
    duplicates = {h: paths for h, paths in hash_map.items() if len(paths) > 1}
    return duplicates, total_files


# Example usage
if __name__ == "__main__":
    folder_path = "."
    dups, total = find_duplicates(folder_path)
    print(f"Found {len(dups)} groups of duplicate files.")
    for files in dups.values():
        print("Duplicate group:")
        for f in files:
            print("  ", f)

    print(f"Total files scanned: {total}")
    print(f"Total duplicates: {len(dups)}")
    print("Percentage: {:.2f}%".format((len(dups) / total) * 100 if total > 0 else 0))
