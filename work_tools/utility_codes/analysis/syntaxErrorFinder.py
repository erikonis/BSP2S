import os
import re
import subprocess

# This script scans Java files in the current directory and its subdirectories,
# checks for syntax errors using javac, and reports the results.


def extract_public_class_name(java_path):
    """
    Extracts the name of the public class from a Java file.
    Returns the class name if found, otherwise None.
    """
    with open(java_path, "r", encoding="utf-8") as f:
        content = f.read()
    match = re.search(r"public\s+class\s+(\w+)", content)
    return match.group(1) if match else None


def check_syntax(java_path):
    """
    Checks the syntax of a Java file using javac.
    If the file contains a public class, ensures the filename matches the class name.
    Temporarily renames the file if needed, then reverts it.
    Returns True if there is a syntax error, False otherwise.
    """
    original_name = os.path.basename(java_path)
    folder = os.path.dirname(java_path)
    class_name = extract_public_class_name(java_path)

    # If no public class or filename matches class name, skip renaming
    if class_name is None or class_name + ".java" == original_name:
        print(f"No public class in {original_name}, skipping strict name check.")
        temp_path = java_path
    else:
        temp_path = os.path.join(folder, class_name + ".java")
        os.rename(java_path, temp_path)

    error = True
    try:
        # Run javac to check for syntax errors
        result = subprocess.run(["javac", temp_path], capture_output=True, text=True)
        if result.returncode == 0:
            error = False
        else:
            print(f"{original_name} syntax error:\n{result.stderr}")
    finally:
        # Revert file rename if it was changed
        if temp_path != java_path:
            os.rename(temp_path, java_path)

        return error


def main():
    """
    Walks through the current directory and subdirectories,
    checks all Java files for syntax errors, and prints a summary.
    """
    folder_path = "."
    files = 0
    error_files = 0
    # Traverse directories and check each Java file
    for dirpath, _, filenames in os.walk(folder_path):
        for fname in filenames:
            if fname.endswith(".java"):
                files += 1
                full_path = os.path.join(dirpath, fname)
                if check_syntax(full_path):
                    error_files += 1

    # Print summary statistics
    print(f"Total Java files scanned: {files}")
    print(f"Files with syntax errors: {error_files}")
    print(
        f"Percentage of files with syntax errors: {error_files / files * 100:.2f}%"
        if files > 0
        else "No Java files found."
    )


if __name__ == "__main__":
    # Entry point for the script
    main()
