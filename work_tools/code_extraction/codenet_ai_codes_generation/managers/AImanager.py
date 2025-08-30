"""
AImanager.py

This module defines the AImanager class, which orchestrates the generation, management, and synchronization of AI-generated solutions for programming exercises.

Key Features:
- Tracks available exercises, models, and languages for solution generation.
- Manages solution generation both sequentially and in parallel, with retry and failure handling.
- Serializes and deserializes state to JSON for persistence and recovery.
- Synchronizes solution counts between output files and internal state.
- Supports extension and restriction of supported languages and models.
- Provides thread-safe operations for parallel generation.

Usage:
- Instantiate AImanager with configuration parameters and model classes.
- Use provided methods to generate solutions, update state, and synchronize with output files.
- Handles cancellation and error recovery gracefully.
"""

import json, importlib
import os
from re import S
import time
from enum import Enum

import threading

from ..managers.JSONmanager import JSONmanager
from ..models.Models import (
    GeminiModel,
    ChatGPTModel,
    ClaudeModel,
    DeepseekModel,
    TestModel,
)


class StopGenerationException(Exception):
    pass


class Keys(Enum):
    meta = "meta"
    api_key = "API_KEY"
    input_dir = "input_dir"
    output_dir = "output_dir"
    languages = "languages"
    model_classes = "model_classes"
    num_of_sols = "number_of_solutions"
    unavailable_ids = "unavailable_ids"
    max_tokens = "max_tokens"
    boolean_test = "test"
    ids = "ids"


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


class AImanager:
    """
    AImanager class desgined to keep track of the operations performed (i.e. generated AI solutions) and synchronize everything to ensure
    that we know what is happening in the generation.
    """

    File_name = "ManagersMeta.json"
    Max_tokens = 5000
    Trials = 2
    _lock = threading.Lock()
    cancelled = False  # for thread stopping mid-generation

    def __init__(
        self,
        API_KEY: str,
        sheets_directory: str,
        output_dir: str,
        languages: list,
        model_classes: list,
        number_of_solutions: int = 5,
        unavailable_ids=None,
        max_tokens: int = Max_tokens,
        TEST: bool = False,
        ids: list = None,
        new_managers: bool = True,
    ):
        """
        Constructor for AImanager. Controls all the json files and updates them whenever generation cycle is used.

        :param API_KEY: The key for the API, obtained from the OpenRouter website.
        :param sheets_directory: The input directory for the exercise sheets. It is always required.
        :param output_dir: The output directory for the exercise sheets. It is always required.
        :param languages: The list, consisting of concerned languages.
        :param model_classes: The list, consisting of concerned models classes. E.g. ChatGPTModel, DeepseekModel, etc.
        :param number_of_solutions: The number of solutions to generate per model (per exercise).
        :param unavailable_ids: The list of unavailable IDs not to use for generation at all. This unavailable IDs are applied to all languages.
        To specify language-specific unavailable IDs, use ``restrictLanguage(unavailable_ids : list)`` function.
        :param max_tokens: The maximum amount of tokens to be used per generation for each of the models.
        :param TEST: Boolean indicating whether this is used for testing or not.
        :param ids: Optional argument. A list of ids. If this argument is given, no lookup into the sheet_directory is done.
        :param new_managers: Boolean indicating whether new JSONmanagers should be created and saved based on languages. If
        this is set to False, it is expected that user will initialize the JSONmanagers manually.
        """

        if unavailable_ids is None:
            unavailable_ids = []

        self.API_KEY = API_KEY  # str
        self.input_dir = sheets_directory  # str
        self.output_dir = output_dir  # str
        self.languages = languages  # list of str
        self.number_of_solutions = number_of_solutions
        self.unavailable_ids = unavailable_ids
        self.max_tokens = max_tokens
        self.test = TEST  # boolean indicating whether this is a test run or not
        self.ids = (
            JSONmanager.idListFromDirectory(self.input_dir) if ids is None else ids
        )

        # Models dictionary initialization from classes
        self.models = {}
        self.model_names = []
        self.JSONmanagers = {}
        self.current_working_manager = None

        if self.test:
            for i, cl in enumerate(model_classes):
                name = "test_" + str(i)
                test_model = TestModel(API_KEY, max_tokens, model_N=name)
                self.models[name] = test_model
                self.model_names.append(test_model.model_name)
        else:
            for model_class in model_classes:
                model_instance = model_class(API_KEY, max_tokens)
                self.models[model_instance.model_name] = model_instance
                self.model_names.append(model_instance.model_name)

        if new_managers:
            self.initialize_managers()

    def initialize_managers(self):
        """
        Initializes the JSON managers for each language, resetting them completely. Caution!
        """
        for language in self.languages:
            self.JSONmanagers[language] = JSONmanager(
                list_of_ids=self.ids,
                models=self.model_names,
                unavailable_ids=self.unavailable_ids,
                number_of_solutions=self.number_of_solutions,
            )
        # print("Managers initialized.")

    def serialize(self, directory: str = "", filename: str = File_name) -> bool:
        """
        Serializes the managers into a JSON file. Filename will be `ManagersMeta.json`

        :param directory: The directory to save the output to.
        :param filename: The filename to save the output to. Default is `ManagersMeta.json`.
        :return: True if succeeded, otherwise False
        """
        output = os.path.join(directory, filename)
        json_object = {
            Keys.meta.value: {
                Keys.api_key.value: self.API_KEY,
                Keys.input_dir.value: self.input_dir,
                Keys.output_dir.value: self.output_dir,
                Keys.languages.value: self.languages,
                Keys.model_classes.value: [
                    model.class_name for model in self.models.values()
                ],
                Keys.num_of_sols.value: self.number_of_solutions,
                Keys.max_tokens.value: self.max_tokens,
                Keys.boolean_test.value: self.test,
                Keys.unavailable_ids.value: self.unavailable_ids,
                Keys.ids.value: self.ids,
            }
        }

        # saving just the json files
        for language in self.languages:
            # json_object[language] = self.JSONmanagers[language].object
            self.JSONmanagers[language].serialize(directory, language + ".json")

        try:
            json_obj = json.dumps(json_object, indent=10)
            with open(output, "w") as f:
                f.write(json_obj)
        except Exception as e:
            print(f"Failed to save!\n{e}")
            return False
        # print("Serialized AImanager successfully to", output)
        return True

    @classmethod
    def deserialize(cls, directory: str = "", filename: str = File_name):
        """
        A class method to deserialize the AImanager from a JSON file.

        :param directory: The directory, where to find the file in to deserialize.
        :param filename: The filename to save the output to. Default is `ManagersMeta.json`.
        :return: The AImanager object deserialized from the file.
        """
        try:
            with open(os.path.join(directory, filename), "r") as f:
                json_object = json.load(f)
        except FileNotFoundError as e:
            raise FileNotFoundError(f"File {directory + filename} not found!\n{e}")
        except Exception as e:
            raise Exception(
                f"Failed to load the AImanager from {directory + filename}!\n{e}"
            )

        meta = Keys.meta.value

        api_key = json_object[meta][Keys.api_key.value]
        input_dir = json_object[meta][Keys.input_dir.value]
        output_dir = json_object[meta][Keys.output_dir.value]
        languages = json_object[meta][Keys.languages.value]
        model_classes = json_object[meta][Keys.model_classes.value]
        num_sols = json_object[meta][Keys.num_of_sols.value]
        unavailable_ids = json_object[meta][Keys.unavailable_ids.value]
        max_tokens = json_object[meta][Keys.max_tokens.value]
        test = json_object[meta][Keys.boolean_test.value]
        ids = json_object[meta][Keys.ids.value]

        # Getting the model classes from the string representation
        models = []
        for model in model_classes:
            model = AImanager.loadClass(model)
            models.append(model)

        instance = AImanager(
            api_key,
            input_dir,
            output_dir,
            languages,
            models,
            num_sols,
            unavailable_ids,
            max_tokens,
            test,
            ids,
            False,
        )

        # Loading other managers from the JSON files
        for language in languages:
            instance.JSONmanagers[language] = JSONmanager.deserialize(
                directory, f"{language}.json"
            )

        # print("Deserialized AImanager successfully!")
        return instance

    def languageCheck(self, language):
        """
        Checks if the language exists inside the AI manager definition.
        :param language: The language to check.
        :return: True if language exists, otherwise False
        """
        if language not in self.languages:
            print(
                f"{bcolors.WARNING}Unexpected language: {language}\nNo such language exists in the definition!{bcolors.ENDC}"
            )
            return False
        return True

    def managersCheck(self, language: str = None):
        """
        Checks if the JSON managers are initialized.
        :return: True if initialized, otherwise False
        """
        if language is None:
            if self.JSONmanagers is None:
                print(
                    f"{bcolors.WARNING}JSON managers are not initialized!{bcolors.ENDC}"
                )
                return False
        else:
            if language not in self.languages:
                print(
                    f"{bcolors.WARNING}No such language: {language} in the definition!{bcolors.ENDC}"
                )
                return False
            elif language not in self.JSONmanagers.keys():
                print(
                    f"{bcolors.WARNING} language. I.e. a manager for {language} does not exist!{bcolors.ENDC}"
                )
        return True

    @staticmethod
    def loadClass(class_name: str, module_path: str = "Models.Models"):
        module = importlib.import_module(module_path)
        obj = getattr(module, class_name)
        # print("Class loaded successfully! (loadClass)")
        return obj

    def _generate_single_solution(
        self,
        json_manager,
        model,
        idx,
        language,
        output,
        max_retries=5,
        base_delay=2,
    ):
        """
        Helper to generate a single solution for a given id/model, handling retries and index logic.
        """

        if self.cancelled:
            raise StopGenerationException("Generation cancelled.")

        next_index = json_manager.getNextAvailableSolutionIndex(idx, model.model_name)
        retries = 0
        while retries < max_retries:
            try:
                response = model.sendExerciseFromFile(
                    self.input_dir,
                    output,
                    language,
                    idx,
                    index=next_index,
                )
                if response:
                    with self._lock:
                        # Ensure thread safety when adding solutions
                        json_manager.addSolution(
                            idx, model.model_name, index=next_index
                        )
                    return True
                else:
                    return False
            except Exception as e:
                delay = base_delay * (2**retries)
                print(
                    f"{bcolors.WARNING}Error: {e}. Retrying in {delay} seconds...{bcolors.ENDC}"
                )
                time.sleep(delay)
                retries += 1
        print("Max retries reached. Serializing state and exiting.")
        self.serialize()
        raise Exception(
            f"Max retries reached for server errors ({retries} out of {max_retries})."
        )

    def generateSolutionSpecific(
        self,
        sheet_id: str,
        model_str: str,
        language: str,
        postfix: str = None,
        num_of_sols: int = 1,
        trials: int = 2,
        max_retries: int = 5,
        base_delay: int = 2,
    ):
        """
        Generates a solution for the given sheet_id and model in the specified language also postfix is manual.
        :param sheet_id: the ID of the exercise sheet to generate a solution for.
        :param model_str: the model to generate a solution (string)
        :param language: the language to generate a solution in.
        :param postfix: postfix to add at the end of the file name. (structured: <model>_<sheet_id>_<postfix>.java)
        :return: True if the solution was generated successfully, otherwise False.
        """

        if self.cancelled:
            raise StopGenerationException("Generation cancelled.")

        if not self.languageCheck(language):
            raise Exception(f"No such language: {language} in the definition!")

        model = self.getModel(language, model_str)
        json_manager = self.getManager(language)

        output = os.path.join(self.output_dir, language)
        os.makedirs(
            output, exist_ok=True
        )  # Create output directory if it doesn't exist
        flag = False
        i = 0
        trial = 0
        while (
            json_manager.needMoreSolutions(sheet_id, model.model_name)
            and i < num_of_sols
            and trial < trials
        ):
            try:
                result = self._generate_single_solution(
                    json_manager,
                    model,
                    sheet_id,
                    language,
                    output,
                    max_retries=max_retries,
                    base_delay=base_delay,
                )
                if result:
                    flag = True
                    i += 1
                    trial = 0
                else:
                    trial += 1
            except StopGenerationException as e:
                raise e

            except Exception as e:
                print(
                    f"{bcolors.FAIL}Error generating solution for ID: {sheet_id} in language: {language}: {e}{bcolors.ENDC}"
                )
                self.serialize()
                raise e
        if trial == trials:
            with self._lock:
                json_manager.addFailure(sheet_id, model.model_name)
            json_manager.monitorFailures(
                num_failures=5, interval_seconds=60, strict=False
            )
        return flag

    def generateSolutionByID(
        self,
        idx: str,
        language: str,
        model_str: str = None,
        num_of_sols: int = 1,
        trials: int = Trials,
        max_retries: int = 5,
        base_delay: int = 2,
    ) -> bool:
        """
        Generates a solution for the given id and model. If the model is not specified, then generates for all models.
        Returns True if at least 1 reponse was successfully generated. False if failed.

        Additionally, if generation fails multiple times, reaching the limit, the (id, model) are logged into the JSON
        manager's failure part in the meta JSON file.

        :param idx: The id of the exercise sheet.
        :param language: The programming language to use. I.e. java, python, etc.
        :param model_str: The model to generate a solution (string). Optional. If omitted, all models are used.
        :param num_of_sols: Number of solutions to generate. If specified high, the maximum allowed number will be generated. Defaults to 1.
        :param trials: Number of trials to generate a solution if generation fails (i.e. AI outputs answer that doesn't contain code). Default is 2.
        :param max_retries: Maximum number of times to retry the generation if an error occurs from server side (i.e. busy). Default is 5.
        :param base_delay: Delay between each generation attempt if server error occures (base for exponential backoff). Default is 2 seconds.
        :return: True if at least 1 solution was generated, otherwise False.
        """

        if self.cancelled:
            raise StopGenerationException("Generation cancelled.")

        if not self.languageCheck(language):
            raise Exception(f"No such language: {language} in the definition!")

        output = os.path.join(self.output_dir, language)
        os.makedirs(output, exist_ok=True)

        json_manager = self.getManager(language)
        flag = False

        # No model specified - we do operation for all models
        if model_str is None:
            models = self.models.values()

        # If model specified - we do operation for the model
        else:
            models = [self.getModel(language, model_str)]

        for model in models:
            i = 0
            trial = 0
            try:
                while (
                    json_manager.needMoreSolutions(idx, model.model_name)
                    and i < num_of_sols
                    and trial < trials
                ):
                    try:
                        result = self._generate_single_solution(
                            json_manager,
                            model,
                            idx,
                            language,
                            output,
                            max_retries=max_retries,
                            base_delay=base_delay,
                        )
                        if result:
                            flag = True
                            i += 1
                            trial = 0
                        else:
                            trial += 1
                    except StopGenerationException as e:
                        self.serialize()
                        raise e
                    except Exception as e:
                        self.serialize()
                        raise e
                if trial == trials:
                    # Logging the failed generation cycle if trials exceeded
                    with self._lock:
                        json_manager.addFailure(idx, model.model_name)
                    json_manager.monitorFailures(
                        num_failures=5, interval_seconds=60, strict=False
                    )
            except Exception as e:
                self.serialize()
                raise e
        return flag

    def generateSolutionForAllIDs(
        self,
        language: str,
        model_str: str = None,
        num_of_sols: int = 1,
        trials: int = Trials,
        max_retries: int = 5,
        base_delay: int = 2,
    ):
        """
        Generates a solution for the given id and model. If the model is not specified, then generates for all models.
        Returns True if at least 1 reponse was successfully generated. False if failed.

        Additionally, if generation fails multiple times, reaching the limit, the (id, model) are logged into the JSON
        manager's failure part in the meta JSON file.

        :param language: The programming language to use. I.e. java, python, etc.
        :param model_str: The model to generate a solution (string). Optional. If omitted, all models are used.
        :param num_of_sols: Number of solutions to generate. If specified high, the maximum allowed number will be generated. Defaults to 1.
        :param trials: Number of trials to generate a solution if generation fails (i.e. AI outputs answer that doesn't contain code). Default is 2.
        :param max_retries: Maximum number of times to retry the generation if an error occurs from server side (i.e. busy). Default is 5.
        :param base_delay: Delay between each generation attempt if server error occures (base for exponential backoff). Default is 2 seconds.
        :return: True if at least 1 solution was generated, otherwise False.
        """

        if self.cancelled:
            raise StopGenerationException("Generation cancelled.")

        for id in self.ids:
            try:
                if not self.generateSolutionByID(
                    id,
                    language,
                    model_str,
                    num_of_sols,
                    trials,
                    max_retries,
                    base_delay,
                ):
                    print(
                        f"{bcolors.WARNING}No solutions generated for ID: {id} in language: {language}!{bcolors.ENDC}"
                    )

            except StopGenerationException as e:
                raise e
            except Exception as e:
                print(
                    f"{bcolors.FAIL}Error generating solution for ID: {id} in language: {language}: {e}{bcolors.ENDC}"
                )
                raise e

    def generateSolutionByIDParallel(
        self,
        idx,
        language,
        model_str=None,
        num_of_sols=1,
        trials=Trials,
        max_retries=5,
        base_delay=2,
    ) -> bool:
        """
        Generates a solution for the given id and model. If the model is not specified, then generates for all models.
        Returns True if at least 1 response was successfully generated. False if failed.

        :param idx: id of the exercise sheet to generate a solution for.
        :param language: The programming language to use. I.e. java, python, etc.
        :param model_str: The model to generate a solution (string). Optional. If omitted, all models are used.
        :param num_of_sols: Number of solutions to generate. If specified high, the maximum allowed number will be generated. Defaults to 1.
        :param trials: Number of trials to generate a solution if generation fails (i.e. AI outputs answer that doesn't contain code). Default is 2.
        :param max_retries: Maximum number of times to retry the generation if an error occurs from server side (i.e. busy). Default is 5.
        :param base_delay: Delay between each generation attempt if server error occures (base for exponential backoff). Default is 2 seconds.
        :return: True if at least 1 solution was generated, otherwise False.
        """

        if self.cancelled:
            raise StopGenerationException("Generation cancelled.")

        if not self.languageCheck(language):
            raise Exception(f"No such language: {language} in the definition!")

        output = os.path.join(self.output_dir, language)
        os.makedirs(output, exist_ok=True)

        json_manager = self.getManager(language)
        flag = False

        if model_str is None:
            models = self.models.values()
        else:
            models = [self.getModel(language, model_str)]

        for model in models:
            i = 0
            trial = 0
            while (
                json_manager.needMoreSolutions(idx, model.model_name)
                and i < num_of_sols
                and trial < trials
            ):
                try:
                    result = self._generate_single_solution(
                        json_manager,
                        model,
                        idx,
                        language,
                        output,
                        max_retries=max_retries,
                        base_delay=base_delay,
                    )
                    if result:
                        flag = True
                        i += 1
                        trial = 0
                    else:
                        trial += 1

                except StopGenerationException as e:
                    return
                except Exception as e:
                    print(
                        f"{bcolors.FAIL}Error generating solution for ID: {idx} in language: {language}: {e}{bcolors.ENDC}"
                    )
                    self.serialize()
                    raise e
            if trial == trials:
                with self._lock:
                    json_manager.addFailure(idx, model.model_name)
                json_manager.monitorFailures(
                    num_failures=5, interval_seconds=60, strict=False
                )
        return flag

    def extendLanguages(self, languages: list) -> bool:
        """
        Extends the current languages inside the AImanager definition and adds another json manager for this language.
        Done so only if the language does not yet exist in the definition.

        :param languages: The list of languages to extend.
        :return: True if something was added. Otherwise False.
        """
        flag = False
        for language in languages:
            if language not in self.languages:
                self.languages.append(language)
                self.JSONmanagers[language] = JSONmanager(
                    list_of_ids=self.ids,
                    models=self.model_names,
                    unavailable_ids=self.unavailable_ids,
                    number_of_solutions=self.number_of_solutions,
                )
                flag = True

        # print("Extended languages successfully!" if flag else "No new languages extended!")
        return flag

    def removeLanguage(self, language: str) -> bool:
        """
        Takes a string as input with specified language and removes it from the AI manager.

        :param language: Language to remove.
        :return: True if successfully removed, False if failed (i.e. no such language exists in the definition).
        """

        flag = False
        if self.languageCheck(language):
            self.languages.remove(language)
            self.JSONmanagers.pop(language)
            flag = True

        # print("Removed language successfully!" if flag else "No such language to remove!")
        return flag

    def restrictLanguage(self, language: str, unavailable_ids: list) -> bool:
        """
        Restricts the chosen language by making the specified ids as "unavailable".
        Informs user if no such id exists.

        :param language: The string representation of the language to restrict.
        :param unavailable_ids: The list of ids.
        :return: True if at least 1 id was restricted, otherwise False.
        """
        if not self.languageCheck(language):
            return False

        json_manager = self.JSONmanagers[language]
        status = json_manager.restrict(unavailable_ids)
        # print("Restricted language successfully!" if status else "No such language to restrict!")
        return status

    def getModel(self, language: str, model: str):
        """
        Find and return the LLM model to perform operations with it.
        :param language: The language to program in.
        :param model: The model representation as a string to return.
        :return: The model if found. Otherwise Exception is raised.
        """
        if self.languageCheck(language):
            if model in self.models.keys():
                # print("getModel successful! Model found and returned.")
                return self.models[model]
            raise Exception(f"No such model: {model}!")
        raise Exception(f"No such language: {language}!")

    def getManager(self, language: str):
        """
        Returns the corresponding JSON manager to a specified programming language.

        :param language: A programming language
        :return: JSON manager for that language. If language incorrect, raises exception.
        """

        if not self.languageCheck(language):
            raise Exception(f"Wrong language!")

        if not self.managersCheck(language):
            raise Exception(f"JSON managers are not initialized!")

        # print("getManager successful! Manager found and returned.")
        return self.JSONmanagers[language]

    def updateNumOfSols(self, num_of_sols: int):
        """
        Updates the number of solutions to generate for the specified language.
        :param language: The programming language to update the number of solutions for.
        :param num_of_sols: The new number of solutions to generate.
        """

        if not self.JSONmanagers:
            raise Exception(f"JSON managers are not initialized!")

        failed_languages = []

        for language in self.languages:
            if not self.JSONmanagers[language].updateMaxSols(num_of_sols):
                failed_languages.append(language)

        self.number_of_solutions = num_of_sols

        if len(failed_languages) > 0:
            raise Exception(
                f"Failed to update the number of solutions to generate for {failed_languages} languages!"
            )

    def resetModelSolutions(self, model: str):
        """
        Resets the number of generated solutions for the given model to 0 for all IDs,
        and updates the total solution counts accordingly.
        """
        if not self.modelCheck(model):
            raise Exception(f"No such model: {model}")

        # Subtract current model's solutions from totals
        total_removed = 0
        for id in self.ids:
            count = self.object[id][Keys.solutions.value][model]
            self.object[id][Keys.solutions.value][model] = 0
            total_removed += count

        self.object[Keys.meta.value][f"{model}{Keys.model_sols_postfix.value}"] = 0
        self.object[Keys.meta.value][Keys.total_sols.value] -= total_removed
        if self.object[Keys.meta.value][Keys.total_sols.value] < 0:
            self.object[Keys.meta.value][Keys.total_sols.value] = 0

        # Update statuses
        for id in self.ids:
            self.updateStatus(id)

    def synchroniseJSON(self) -> bool:
        """
        Synchronizes the JSON manager with the output directory for all languages.
        """
        flag = True
        for lang in self.languages:
            if not self.synchroniseJSONlanguage(lang):
                print(
                    f"{bcolors.WARNING}Synchronization failed for language: {lang}.{bcolors.ENDC}"
                )
                flag = False

        return flag

    def synchroniseJSONlanguage(self, language: str) -> bool:
        """Iterates through files in the output directory and synchronizes the JSON manager with the files.
        Informs if there was a mismatch between the JSON manager and the files in the output directory.
        The updated JSON manager is serialized to <language>2.json file in the current directory.
        :param language: The programming language to synchronize.

        :return: True if no mismatches were found, False otherwise.
        """

        if not self.languageCheck(language):
            raise Exception(f"No such language: {language} in the definition!")

        json_manager = self.getManager(language)
        json_manager.serialize()
        out_dir = os.path.join(self.output_dir, language)
        temp_dict = {}

        # Count solutions from files
        for fname in os.listdir(out_dir):
            fpath = os.path.join(out_dir, fname)
            if os.path.isfile(fpath):
                filename, ext = os.path.splitext(fname)
                try:
                    model, pid, index = filename.split("_")
                except ValueError:
                    continue  # skip files not matching the pattern
                temp_dict.setdefault(pid, {}).setdefault(model, 0)
                temp_dict[pid][model] += 1

        mismatched_ids = set()
        mismatch = False
        # Compare with JSON manager and update if needed
        for pid in temp_dict:
            for model in temp_dict[pid]:
                file_count = temp_dict[pid][model]
                json_count = json_manager.getSolutionNumber(pid, model)
                if file_count != json_count:
                    mismatched_ids.add(pid)
                    mismatch = True
                    # Update JSON manager to match file count
                    json_manager.setSolutionNumber(pid, model, file_count)

        if mismatch:
            # Serialize the updated JSON manager to a new file
            output_path = os.path.abspath(out_dir)
            json_manager.serialize(directory="", filename=f"{language}_mismatched.json")
            json_manager = JSONmanager.deserialize()

            print(
                f"{bcolors.WARNING}Mismatch found between JSON manager and files in {output_path}!{bcolors.ENDC}"
            )
            print(f"{bcolors.WARNING}Mismatched IDs found: {mismatched_ids}")
            return False

        print(
            f"{bcolors.OKGREEN}JSON manager synchronized successfully with files in {output_path}.{bcolors.ENDC}"
        )
        return True

    def cancelGeneration(self):
        """
        Cancels the ongoing generation process.
        """
        self.cancelled = True
