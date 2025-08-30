"""
LLM_Model.py

This module defines the base Model class for interacting with large language models (LLMs) to generate code solutions for programming exercises.

Key Features:
- Loads exercises from files and sends prompts to LLMs via OpenAI API.
- Extracts code blocks from LLM responses and saves them to output files.
- Handles multiple programming languages and file extensions.
- Logs errors and responses for debugging and traceability.
- Provides utility methods for code extraction and file management.

Usage:
- Instantiate Model with API key, model name, and configuration.
- Use sendExerciseFromFile or sendExercise to generate and save code solutions.
"""

import random
from datetime import datetime

from openai import OpenAI
import os
import re


class bcolors:
    HEADER = "\033[95m"
    OKBLUE = "\033[94m"
    OKCYAN = "\033[96m"
    OKGREEN = "\033[92m"
    WARNING = "\033[93m"
    FAIL = "\033[91m"
    ENDC = "\033[0m"
    BOLD = "\033[1m"
    UNDERLINE = "\033[4m"


# ---------------HYPER PARAMETER---------------

# It contains phrases that students tend to add to the end when asking to solve the exercise.
STUDENT_SALT = [
    "Solve it:",
    "Solve this, use alternative method:",
    "Give another solution for this:",
    "Solve this:",
    "Give me a solution for this:",
    "Solve it efficiently:",
    "Help me with this:",
]


class Model:

    DEFAULT_TEMPERATURE = 0.7
    ANSWERS_DIR = "LLM_answers"
    LOG_FILE = "response_logs.txt"

    def __init__(self, api: str, max_tokens: int, model: str, model_name: str):
        """
        :param api: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        :param model: model name as copied from the OpenRouter website.
        """
        self.api = api
        self.max_tokens = max_tokens
        self.model = model
        self.model_name = model_name
        self.client = OpenAI(base_url="https://openrouter.ai/api/v1", api_key=api)

    def sendExerciseFromFile(
        self,
        input_dir: str,
        output_dir: str,
        language: str,
        id: str,
        index: int = None,
        extension: str = "html",
        temperature: float = DEFAULT_TEMPERATURE,
    ):
        """
        Opens some file, reads it as text plain file, then takes the content and submits it to
        the according model, retrieves answer and saves it in the specified output directory.
        :param temperature: Randomness of the model. 0 - least random (best for maths),
        :param input_dir: The directory to read the file from.
        :param output_dir: The Output directory
        :param language: The programming language used(e.g. java, python, cpp, etc)
        :param id: The ID of the exercise
        :param index: The index of the exercise, used for saving files. If None, there won't be any.
        :param extension: The extension of the file
        :param temperature: The temperature of the model
        :return: Boolean. True if at least one code block was detected and saved, False if none.
        """
        prompt = ""

        try:
            with open(
                os.path.join(input_dir, id + "." + extension), "r", encoding="utf-8"
            ) as file:
                prompt = file.read()

        except FileNotFoundError:
            print("\nInput file not found!")
            raise FileNotFoundError(
                "Input file not found!"
                + f"\nInput directory: {input_dir} | ID: {id} | Extension: {extension}"
            )

        return self.sendExercise(prompt, output_dir, language, id, index, temperature)

    def sendExercise(
        self,
        exercise: str,
        output: str,
        language: str,
        id: str,
        index: int = None,
        temperature: float = DEFAULT_TEMPERATURE,
    ) -> bool:
        """
        Takes the exercise (prompt) as input and passes it to the corresponding LLM, retrieves answer and
        saves it in the specified output path. If path does not exist, it is created.

        :param temperature: Default is 0.7. Defines how creative the model is. 0.0 = deterministic, more than 1.2 is very creative, but also unpredictable.
        :param exercise: The exercise (string). Aka prompt.
        :param output: Output directory
        :param language: Programming language used. E.g. (java, python, cpp, etc)
        :param id: ID of the exercise
        :param index: Index of the exercise, used for saving files. If None, there won't be any.
        :return: Boolean. True if at least one code block was detected and saved, False if none.
        """
        try:
            language_postfix = self.languageToPostfix(language)
        except Exception:
            print("\nLanguage not recognized!")
            raise Exception(
                "Language not recognized! Please use one of the following: "
                "java, python, cpp, c++, c#"
            )

        if not os.path.exists(output):
            os.makedirs(output)

        response = None
        try:
            print(f"Processing prompt... ID: {id}", end=" -> ")
            model_info = self.model.split(
                "/"
            )  # `google/gemini-2.5v-pro` -> [`google`, `gemini-2.5v-pro`]

            response = self.client.chat.completions.create(
                model=self.model,
                messages=[
                    {
                        "role": "system",
                        "content": f"You are LLM based helpful assistant from {model_info[0]}, asked to solve a programming exercise."
                        f"The code has to be in {language} language and in triple backsticks, specify the language (e.g. ```java\n<code>\n```)."
                        "Solve the exercise in a single file."
                        "If you use comments, avoid big explanations and keep them concise."
                        "Only return the code in the answer, do not include any extra commentary, headings, or descriptions.",
                    },
                    {
                        "role": "user",
                        "content": random.choice(STUDENT_SALT) + "\n\n" + exercise,
                    },
                ],
                max_tokens=self.max_tokens,
                temperature=temperature,
            )

            # --- ERROR CHECKING ---
            if response is None:
                print(f"\nError: LLM API response for ID {id} was None.")
                self.logError(
                    f"LLM API response was None for ID {id} with model {self.model_name}",
                    answer="Response object was None",
                    output=self.LOG_FILE,
                )
                return False  # Indicate failure

            if not hasattr(response, "choices") or not response.choices:
                print(
                    f"\nError: LLM API response for ID {id} did not contain 'choices' or 'choices' was empty."
                )
                self.logError(
                    f"LLM API response missing 'choices' or 'choices' was empty for ID {id} with model {self.model_name}",
                    answer="Response:\n" + str(response),
                    output=self.LOG_FILE,
                )
                return False  # Indicate failure

            if (
                not hasattr(response.choices[0], "message")
                or not response.choices[0].message
            ):
                print(f"\nError: First choice message for ID {id} was None or missing.")
                self.logError(
                    f"First choice message was None or missing for ID {id} with model {self.model_name}",
                    answer="Answer:\n" + str(response.choices[0]),
                    output=self.LOG_FILE,
                )
                return False  # Indicate failure

            answer = response.choices[0].message.content.strip()

            # Extracting code
            contents, no_lang_contents = self.extractCode(answer, language)
            index_str = "" if index is None else f"_{index}"

            # Save response to a code files.
            flag = self.saveCode(
                answer, contents, id, index_str, language_postfix, output
            )

            ans_dir = os.path.join(output, self.ANSWERS_DIR)
            os.makedirs(ans_dir, exist_ok=True)
            filename = os.path.join(ans_dir, f"{self.model_name}_{id}{index_str}.txt")

            with open(filename, "w", encoding="utf-8") as f:
                f.write(answer)

            if len(no_lang_contents) > 0:
                flag = check_output = os.path.join(output, "CHECK_THIS")
                self.saveCode(
                    answer,
                    no_lang_contents,
                    id,
                    index_str,
                    language_postfix,
                    check_output,
                )
                print("\n-------------------------\n")
                print("No language detected for some code blocks!")
                print(
                    "Please check the CHECK_THIS directory for code blocks that were not saved due to missing language tags."
                )

            return flag

        except Exception as e:
            print(f"\nError processing prompt #{id}: {e}")

            ans = (
                f"Exception:\n {str(e)}"
                if response is None
                else f"Response:\n {response}"
            )

            self.logError(
                f"Error processing prompt #{id} with model {self.model_name}",
                answer=ans,
                output=self.LOG_FILE,
            )
            raise e

    def saveCode(self, answer, contents, id, index_str, language_postfix, output):
        """
        Saves the code blocks extracted from the answer into files.
        If multiple code blocks are found, they are saved in a directory with an index.
        If only one code block is found, it is saved as a single file.
        :param answer: The answer string from the LLM response.
        :param contents: List of code blocks extracted from the answer.
        :param id: The ID of the exercise.
        :param index_str: The index string for saving files, if applicable.
        :param language_postfix: The file extension for the programming language.
        :param output: The output directory where files will be saved.
        :return: True if at least one code block was saved, False otherwise.
        """

        os.makedirs(output, exist_ok=True)

        if not contents:
            print(f"{bcolors.WARNING}No code blocks found for ID: {id}.{bcolors.ENDC}")
            print("\n-------------------------\n")
            print(f"ID: {id} | model: {self.model_name}")
            print("\n--------------------------\n")

            self.logError(
                "No code blocks detected!"
                f"\n ID: {id} | model: {self.model_name}"
                "\n Answer",
                answer,
            )

            return False

        if len(contents) > 1:
            directory = os.path.join(output, f"{self.model_name}_{id}{index_str}")
            os.makedirs(directory, exist_ok=True)
            for idx, code in enumerate(contents):
                filename = os.path.join(
                    directory,
                    f"{self.model_name}_{id}{index_str}_p{idx}.{language_postfix}",
                )
                with open(filename, "w", encoding="utf-8") as f:
                    f.write(code)
                print(f"Saved to {filename}", end=" | ")
        else:
            filename = os.path.join(
                output, f"{self.model_name}_{id}{index_str}.{language_postfix}"
            )
            with open(filename, "w", encoding="utf-8") as f:
                f.write(contents[0])
            print(f"Saved to {filename}", end=" | ")

        return True

    def extractCode(self, answer, language):
        """
        Extracts code blocks from the answer string based on the specified programming language.

        :param answer: The answer string from the LLM response.
        :param language: The programming language to filter code blocks (e.g., "python", "java", etc.).
        :return: List with code blocks extracted from the answer (strings).
        """

        language = language.lower()
        code_blocks = re.findall(r"```(?:([\w+-]+)[ \t]*)?\n?([\s\S]*?)```", answer)
        extracted = []
        no_lang = []
        for lang, code in code_blocks:
            if lang and lang.lower() == language:
                extracted.append(code.strip())
            elif not lang:
                no_lang.append(code.strip())
        return extracted, no_lang

    @staticmethod
    def languageToPostfix(language: str) -> str:
        """Maps language names to file extensions."""
        mapping = {
            "java": "java",
            "python": "py",
            "py": "py",
            "javascript": "js",
            "js": "js",
            "c++": "cpp",
            "cpp": "cpp",
            "c": "c",
            "c#": "cs",
            "csharp": "cs",
            "go": "go",
            "ruby": "rb",
            "rust": "rs",
            "kotlin": "kt",
            "scala": "scala",
            "swift": "swift",
            "typescript": "ts",
            "ts": "ts",
            "php": "php",
            "html": "html",
            "css": "css",
            "r": "r",
            "matlab": "m",
            "perl": "pl",
            "dart": "dart",
        }
        lang = language.lower()
        if lang in mapping:
            return mapping[lang]
        raise Exception("\nCould not resolve file type!")

    def logError(self, info: str, answer: str, output: str = LOG_FILE):
        """
        Log an error with information into a log file. Creates output directory if it doesn't exist yet.
        :param info: Information about error, logging message.
        :param answer: Answer from the LLM or API.
        :param output: Where to log. Default is "response.log"
        """

        log_dir = os.path.dirname(output)

        if log_dir:
            os.makedirs(log_dir, exist_ok=True)

        with open(output, "a", encoding="utf-8") as f:
            f.write(
                f"{datetime.now()}\n\n" f"{info}: \n\n" f"{answer}\n" f"{'-' * 50}\n\n"
            )
