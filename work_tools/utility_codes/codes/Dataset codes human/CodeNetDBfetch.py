import os
import pandas as pd
from datasets import load_dataset
from tqdm import tqdm

"""
    Code is used to fetch and export the CodeNet dataset for a specific programming language.
    It loads the dataset, processes it, and saves the code files along with metadata in a structured format.
    Creates a folder for each problem ID, within creates folders for each status (e.g., "accepted", "rejected"),
    and saves the code files with appropriate extensions based on the target language.
"""

# Configurable: select your target language
TARGET_LANGUAGE = "Java"

# Optional fallback for file extensions
EXTENSION_MAP = {
    "Ada": ".adb",
    "Python": ".py",
    "Java": ".java",
    "C++": ".cpp",
    "C": ".c",
    "Ruby": ".rb",
    "Go": ".go",
    "Haskell": ".hs",
    "Text": ".txt"
}

def safe_filename(name: str) -> str:
    """Sanitize file and folder names"""
    return "".join(c if c.isalnum() or c in ('-', '_') else '_' for c in str(name))

def export_language(language: str):
    print(f"[+] Loading dataset for language: {language}")
    dataset = load_dataset("iNeil77/CodeNet", language, split="train")

    print(f"[✓] Loaded {len(dataset)} entries.")

    df = dataset.to_pandas()
    lang_folder = safe_filename(language)
    os.makedirs(lang_folder, exist_ok=True)

    print(f"[+] Saving metadata CSV to: {lang_folder}")
    df.to_csv(os.path.join(lang_folder, f"{safe_filename(language)}_metadata.csv"), index=False)

    print("[+] Exporting individual code files...")

    # Determine file extension
    ext = EXTENSION_MAP.get(language, ".txt")

    for _, row in tqdm(df.iterrows(), total=len(df), desc="Writing files"):
        code = row["code"]
        p_id = safe_filename(row["p_id"])
        s_id = safe_filename(row["s_id"])
        status = safe_filename(row["status"])

        problem_dir = os.path.join(lang_folder, p_id)
        status_folder = os.path.join(problem_dir, status)
        os.makedirs(status_folder, exist_ok=True)

        filepath = os.path.join(status_folder, s_id + ext)

        with open(filepath, "w", encoding="utf-8") as f:
            f.write(code)

    print("[✓] Export completed.")

if __name__ == "__main__":
    export_language(TARGET_LANGUAGE)
