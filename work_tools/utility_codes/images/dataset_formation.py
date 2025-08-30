import os
import random
import shutil
import concurrent.futures
from tqdm import tqdm
import threading

"""
This script organizes images into a dataset structure for training and testing.
It checks the provided POOl directories for according categories, scans them.
Then the `test` and `train` directories are created with subdirectories for each category
(in particular, `0` for human codes and `1` for AI generated codes).

Pooled images are moved into the respective directories.

NOTE:
- Set HUMAN_POOL_DIR and AI_POOL_DIR to the directories containing your images before running.
- If there are fewer images than SAMPLES_PER_CATEGORY in a category, all images will be moved to the test set!
"""


HUMAN_POOL_DIR = "Java_20perEx_img_hum_preproc_pool"
AI_POOL_DIR = "Java_20perEx_img_ai_preproc_pool"
OUTPUT_DIR = "output"
SAMPLES_PER_CATEGORY = 3388 #3388 * 2 = 6776 == 10% * 67760 images in total

def create_readme(directory):
    readme_content = "Label descriptions:\n0 - Human codes\n1 - AI generated codes"
    with open(os.path.join(directory, 'readme.txt'), 'w') as f:
        f.write(readme_content)

def move_file(src, dst, pbar, lock):
    shutil.move(src, dst)
    with lock:
        pbar.update(1)

def organize_images(class0_dir, class1_dir, base_output_dir, samples_per_category=1000):
    # Create main output directories
    os.makedirs(base_output_dir, exist_ok=True)
    test_dir = os.path.join(base_output_dir, 'test')
    train_dir = os.path.join(base_output_dir, 'train')
    os.makedirs(test_dir, exist_ok=True)
    os.makedirs(train_dir, exist_ok=True)
    
    # Create readme files
    create_readme(test_dir)
    create_readme(train_dir)
    
    # Dictionary to store files by their labels
    label_files = {'0': [], '1': []}
    
    # Scan source directory and categorize files
    for filename in os.listdir(class0_dir):
        if filename.lower().endswith('.png'):
            file_path = os.path.join(class0_dir, filename)
            label_files['0'].append((file_path, filename))

    for filename in os.listdir(class1_dir):
        if filename.lower().endswith('.png'):
            file_path = os.path.join(class1_dir, filename)
            label_files['1'].append((file_path, filename))
    
    # Process each category (0 and 1)
    for label, tups in label_files.items():
        # Create category directories in both test and train
        test_category_dir = os.path.join(test_dir, label)
        train_category_dir = os.path.join(train_dir, label)
        os.makedirs(test_category_dir, exist_ok=True)
        os.makedirs(train_category_dir, exist_ok=True)
        
        # Select random samples for test set
        if len(tups) >= samples_per_category:
            test_files = random.sample(tups, samples_per_category)
            train_files = [t for t in tups if t not in test_files]
        else:
            print("NOT ENOUGH SAMPLES FOR CATEGORY: ", label)
            test_files = tups
            train_files = []
        
        with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
            lock = threading.Lock()
            # Copy test files
            with tqdm(total=len(test_files), desc="Moving test set files") as pbar:
                futures = [executor.submit(move_file, tup[0], os.path.join(test_category_dir, tup[1]), pbar, lock) for
                           tup in test_files]
                concurrent.futures.wait(futures)
            # Copy train files
            with tqdm(total=len(train_files), desc="Moving train set files") as pbar:
                futures = [executor.submit(move_file, tup[0], os.path.join(train_category_dir, tup[1]), pbar, lock) for
                           tup in train_files]
                concurrent.futures.wait(futures)

if __name__ == "__main__":
    output_directory = "dataset"
    organize_images(HUMAN_POOL_DIR, AI_POOL_DIR, OUTPUT_DIR, SAMPLES_PER_CATEGORY)