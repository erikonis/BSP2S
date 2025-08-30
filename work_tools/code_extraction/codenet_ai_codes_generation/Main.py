"""
Main.py

This script automates the generation of programming exercise solutions using multiple AI models.
It supports both linear (sequential) and parallel (multi-threaded) solution generation for a set of exercises.

Key Features:
- Loads configuration and model parameters from a JSON file.
- Uses an AI manager to orchestrate solution generation and serialization.
- Supports multiple programming languages and AI models (e.g., ChatGPT, Claude, Deepseek, Gemini).
- Handles unavailable exercise IDs and manages state persistence.
- Provides notification sounds upon completion.

Usage:
- Run the script directly to generate solutions either linearly or in parallel by uncommenting the desired function call at the bottom.
- The script will automatically detect if it is the first run or a continuation based on the presence of the manager file.
"""

# Standard library imports
import time  # For timing and delays
import json  # For reading configuration files
import winsound  # For notification sounds
import concurrent  # For parallel execution
import os  # For file and path operations

# Project-specific imports
from managers.AImanager import AImanager  # AI manager for solution generation
from managers.JSONmanager import JSONmanager  # JSON manager for unavailable IDs
from models.Models import *  # Import all model classes

# --------------------------------------------------
# Main script for generating code solutions using AI models
# --------------------------------------------------


# ----------------HYPER PARAMETERS-----------------
# Load configuration from config.json
with open("config.json", "r") as f:
    config = json.load(f)

API_KEY = config["API_KEY"]  # API key for AI models
OUTPUT_DIR = config["OUTPUT_DIR"]  # Directory for output solutions
INPUT_DIR = config["INPUT_DIR"]  # Directory for input exercise sheets
UNAVAILABLE_DIR = config["UNAVAILABLE_DIR"]  # Directory for unavailable IDs
LANGUAGES = config["LANGUAGES"]  # List of programming languages
MODEL_CLASSES = [
    ChatGPTModel,
    ClaudeModel,
    DeepseekModel,
    GeminiModel,
]  # AI model classes
MAX_SOLS = config["MAX_SOLS"]  # Max number of solutions per exercise
MAX_TOKENS = config["MAX_TOKENS"]  # Max tokens per solution

first_time = True  # Flag to indicate first run


def generate_linearly(
    first_time: bool = True,
    api_key: str = API_KEY,
    input_dir: str = INPUT_DIR,
    output_dir: str = OUTPUT_DIR,
    languages: list = LANGUAGES,
    model_classes: list = MODEL_CLASSES,
    number_of_solutions: int = MAX_SOLS,
    unavailable_dir: str = UNAVAILABLE_DIR,
    max_tokens: int = MAX_TOKENS,
):
    """
    Generate solutions for exercise sheets in the input directory sequentially (no parallelization).
    - Slower than parallel version (~422 solutions/hour).
    - Serializes manager state as JSON after completion.
    """

    if first_time:
        # Construct a new AI manager instance
        unavailable_ids = JSONmanager.unavailableIdsFromCSV(
            unavailable_dir
        )  # Get unavailable IDs
        manager = AImanager(
            api_key,
            input_dir,
            output_dir,
            languages,
            model_classes,
            number_of_solutions=number_of_solutions,
            unavailable_ids=unavailable_ids,
            max_tokens=max_tokens,
        )
        print("Created new AImanager instance.")
    else:
        # Load manager from file
        manager = AImanager.deserialize()
        print("Deserialized AImanager from file.")

    available_ids = manager.ids  # List of available exercise IDs

    # Generate solutions for each language and ID
    try:
        for lang in manager.languages:
            for id in available_ids[0:450]:  # Adjust range as needed
                manager.generateSolutionByID(id, lang, num_of_sols=number_of_solutions)

    except KeyboardInterrupt:
        print("Cancelling generation...")
        manager.cancelGeneration()

    except Exception as e:
        raise e

    finally:
        print("Finished generating solution.")
        manager.serialize()  # Save manager state

        # Play notification sound when finished
        for i in range(3):
            winsound.MessageBeep(winsound.MB_ICONHAND)
            time.sleep(1)  # Sleep for a second to ensure the beep is heard


def generate_parallel(
    first_time: bool = True,
    api_key: str = API_KEY,
    input_dir: str = INPUT_DIR,
    output_dir: str = OUTPUT_DIR,
    languages: list = LANGUAGES,
    model_classes: list = MODEL_CLASSES,
    number_of_solutions: int = MAX_SOLS,
    unavailable_dir: str = UNAVAILABLE_DIR,
    max_tokens: int = MAX_TOKENS,
):
    """
    Generate solutions for exercise sheets in the input directory in parallel (multi-threaded).
    - Faster than linear version (~1180 solutions/hour).
    - Serializes manager state as JSON after completion.
    """

    if first_time:
        # Construct a new AI manager instance
        unavailable_ids = JSONmanager.unavailableIdsFromCSV(unavailable_dir)
        manager = AImanager(
            api_key,
            input_dir,
            output_dir,
            languages,
            model_classes,
            number_of_solutions=number_of_solutions,
            unavailable_ids=unavailable_ids,
            max_tokens=max_tokens,
        )
        print("Created new AImanager instance.")
    else:
        # Load manager from file
        manager = AImanager.deserialize()
        print("Deserialized AImanager from file.")

        # Synchronize JSON state if needed
        if manager.synchroniseJSON():
            print("AImanager JSON is synchronized.")
        else:
            raise ValueError("Failed to synchronize AImanager JSON.")

    available_ids = manager.ids  # List of available exercise IDs
    models = manager.model_names  # List of model names

    # Generate solutions for each language and ID in parallel
    try:
        for lang in manager.languages:
            with concurrent.futures.ThreadPoolExecutor(
                max_workers=len(models)
            ) as executor:
                futures = []
                for idx in available_ids[0:450]:  # Adjust range as needed
                    for model in models:
                        futures.append(
                            executor.submit(
                                manager.generateSolutionByIDParallel,
                                idx,
                                lang,
                                model,
                                number_of_solutions,
                            )
                        )
            # Wait for all threads to finish and handle exceptions
            for future in concurrent.futures.as_completed(futures):
                try:
                    result = future.result()
                except Exception as exc:
                    print(f"Generation failed: {exc}")

        print("Finished generating solutions in parallel.")

    except KeyboardInterrupt:
        print("Cancelling generation...")
        manager.cancelGeneration()
        executor.shutdown(wait=True, cancel_futures=True)

    except Exception as e:
        print(f"An error occurred: {e}. Serialized state to file.")

    finally:
        manager.serialize()  # Save manager state
        print("Serialized AImanager state to file.")
        # Play notification sound when finished
        for i in range(3):
            winsound.MessageBeep(winsound.MB_ICONHAND)
            time.sleep(1)  # Sleep for a second to ensure the beep is heard


# Entry point for script execution
if __name__ == "__main__":
    # Check if manager file exists to determine if it's the first run
    if os.path.exists(AImanager.File_name):
        FIRST_TIME = False

    # Uncomment one of the following to run the desired generation mode:
    # generate_linearly(FIRST_TIME)
    # generate_parallel(FIRST_TIME)
