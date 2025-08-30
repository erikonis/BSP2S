"""
JSONmanager.py

This module defines the JSONmanager class, which tracks and manages the status, progress, and metadata of AI-generated solutions for programming exercises.

Key Features:
- Maintains solution counts, statuses, and failure logs for each exercise and model.
- Serializes and deserializes state to JSON for persistence and recovery.
- Provides utility methods for extracting IDs from directories and unavailable IDs from CSV files.
- Supports updating solution counts, restricting IDs, and monitoring failures.
- Designed for integration with AI solution generation workflows.

Usage:
- Instantiate JSONmanager with lists of exercise IDs and model names.
- Use methods to update progress, serialize/deserialize state, and manage solution metadata.
"""

import json
import os
from enum import Enum
import csv
from datetime import datetime


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


# ---------HYPERPARAMETERS--------
class Status(Enum):
    unavailable = "unavailable"
    ready = "ready"
    in_progress = "in_progress"
    done = "done"
    over = "over"  # Not used, but can be used for future purposes


class Keys(Enum):
    meta = "meta"
    models = "models"
    max_sols = "max_sols"
    ids_num = "ids_num"
    unavailable_ids = "unavailable_ids"
    total_sols = "total_solutions"
    model_sols_postfix = "_solutions"
    failures = "failures"
    ids = "ids"
    status = "status"
    solutions = "solutions"
    sol_indices = "solution_indices"
    individual_id = "id"
    individual_model = "model"
    timestamp = "timestamp"


# ----------------------------


# ---------FOR TESTING:----------
class Models(Enum):
    chatgpt = "chatgpt"
    claude = "claude"
    deepseek = "deepseek"
    gemini = "gemini"


MODELS = [
    Models.chatgpt.name,
    Models.claude.name,
    Models.deepseek.name,
    Models.gemini.name,
]

# -------------------------------


class JSONmanager:
    """
    JSONmanager to track the status and progress of AI code generation based on the exercise sheets that are identified by their 'ids'
    encoded into the filename. Class provides a static method ``idListFromDirectory`` that can output a list of ids from a given directory files.
    File expects filename structure like this: `someID.extension`.
    """

    STATUS = [
        Status.unavailable.value,
        Status.ready.value,
        Status.in_progress.value,
        Status.done.value,
        Status.over.value,
    ]
    DEFAULT_FILE_NAME = "SolutionsMeta.json"
    DEFAULT_NUM_OF_SOLS = 5  # maximum number of solutions per model
    DEFAULT_IDENTATION_JSON = 10  # default identation for JSON files

    def __init__(
        self,
        list_of_ids: list,
        models: list,
        unavailable_ids=None,
        number_of_solutions=DEFAULT_NUM_OF_SOLS,
        new_manager: bool = True,
    ):
        """
        Initializes the JSON manager.

        :param list_of_ids: The list of the ids of the exercise sheets (strings). Can be obtained using static methods ``idListFromDirectory``.
        :param models: The list of models, containing string representations for the models.
        :param unavailable_ids:  The list of unavailable ids (strings)
        :param number_of_solutions: The number of solutions needed to solve the problem. Integer.
        :param new_manager: Whether the new manager is being created or not. Default is True. If False, user is expected to assign json object to manager manually.
        """

        if unavailable_ids is None:
            unavailable_ids = []

        self.ids = list_of_ids  # List
        self.models = models  # List
        self.unavailable_ids = unavailable_ids  # Set

        self.max_sols = number_of_solutions

        self.object = None  # Dictionary

        # Initialization -> setting the above values
        if new_manager:
            self.newObject(list_of_ids, models, unavailable_ids, number_of_solutions)

    def newObject(
        self,
        list_of_ids: list,
        models: list,
        unavailable_ids=None,
        number_of_sols=DEFAULT_NUM_OF_SOLS,
    ) -> dict:
        """
        Outputs a new dictionary object ready to be saved as JSON file. Used for tracking the progress. Is of a form:

        {
        "meta" : {  "models" : ["model1", "model2", "model3"],
                    "ids_num" : 2,
                    "max_sols" : 5,
                    "total_solutions" : 0,
                    "model1_solutions" : 0,
                    "model2_solutions" : 0,
                    "model3_solutions" : 0
                    "failures" : [("id001", "model1")],
                    "unavailable_ids" : []
                }
        "id1" : {   "status" : "unavailable",
                    "solutions": {  "model1" : 0,  #here is the number of generated solutions
                                    "model2" : 0,
                                    "model3" : 0},
        "id2" : {...}
        }

        :param list_of_ids: a list of all the ids. Can be read
        :param models: a list of all the models (LLM) to be tracked.
        :param unavailable_ids: List of unavailable ids. I.e. that should not be considered for code generation.
        :param number_of_sols: the maximum number of solutions per model.

        :return: The object created (dictionary)
        """

        if unavailable_ids is None:
            unavailable_ids = []

        if len(models) == 0:
            raise ValueError("You must provide a non-empty list of models!")

        # INITIALIZATION OF DICTIONARY
        dictionary = {}
        solutions_dictionary = {}

        for model in models:
            solutions_dictionary[model] = 0

        dictionary[Keys.meta.value] = {
            Keys.models.value: models,
            Keys.ids_num.value: len(list_of_ids),
            Keys.max_sols.value: number_of_sols,
            Keys.total_sols.value: 0,
        }

        # Populating metadata with solution info for each model.
        for model in models:
            dictionary[Keys.meta.value][f"{model}{Keys.model_sols_postfix.value}"] = 0
        dictionary[Keys.meta.value][Keys.failures.value] = []
        dictionary[Keys.meta.value][Keys.unavailable_ids.value] = unavailable_ids

        # JSONmanager INITIALIZATION
        unavailable_ids = set(unavailable_ids)  # For optimization purposes

        # Setting the status for each id (checking among unavailable ids)
        for id in list_of_ids:
            dictionary[id] = {
                Keys.status.value: (
                    Status.unavailable.value
                    if id in unavailable_ids
                    else Status.ready.value
                ),
                Keys.solutions.value: solutions_dictionary.copy(),
                Keys.sol_indices.value: {},  # Initialize solution indices tracking
            }

        self.unavailable_ids = unavailable_ids  # set
        self.models = models  # list
        self.ids = list_of_ids  # list
        self.object = dictionary
        self.max_sols = number_of_sols
        return dictionary

    def serialize(self, directory: str = "", filename: str = DEFAULT_FILE_NAME) -> bool:
        """
        Serializes the object into a JSON file. Filename will be `SolutionsMeta.json`

        :param directory: The directory to save the output to.
        :param filename: The filename of the output. Default is `SolutionsMeta.json`.
        :return: True if succeeded, otherwise False
        """
        output = os.path.join(directory, filename)
        self.object[Keys.meta.value][Keys.unavailable_ids.value] = list(
            self.unavailable_ids
        )
        self.object[Keys.meta.value][Keys.models.value] = self.models
        self.object[Keys.meta.value][Keys.max_sols.value] = self.max_sols

        try:
            json_obj = json.dumps(self.object, indent=self.DEFAULT_IDENTATION_JSON)
            with open(output, "w", encoding="utf-8") as f:
                f.write(json_obj)
        except Exception as e:
            print(f"Failed to save!\n{e}")
            return False
        return True

    @classmethod
    def deserialize(cls, directory: str = "", filename: str = DEFAULT_FILE_NAME):
        """
        Loads an object from a given JSON file. Replaces all values of this object by the loaded object.

        :param directory: The directory to save the output to. Default is "SolutionsMeta.json".
        :return: Loaded object (dictionary)
        :param filename: The filename of the output. Default is `SolutionsMeta.json`.
        """
        destination = os.path.join(directory, filename)

        try:
            with open(destination, "r", encoding="utf-8") as file:
                obj = json.load(file)
                metadata = obj[Keys.meta.value]
                ids = [key for key in obj.keys() if key != Keys.meta.value]
                models = metadata[Keys.models.value]
                unavailable_ids = metadata[Keys.unavailable_ids.value]
                max_sols = metadata[Keys.max_sols.value]

                manager = JSONmanager(
                    ids, models, unavailable_ids, max_sols, new_manager=False
                )
                manager.object = obj

                return manager

        except FileNotFoundError as e:
            print(f"Failed to load object from destination {destination}!\n{e}")
            raise FileNotFoundError(f"File not found: {destination}")
        except Exception as e:
            print("Something else went wrong while loading the object!")
            raise Exception(e)

    def addSolution(
        self,
        id: str,
        model: str,
        index: int,
        num_of_solutions: int = 1,
        force: bool = False,
    ) -> bool:
        """
        Adds a solution for the given model and id. If index is provided, tracks the solution index for gap filling.
        :param id: The id of the exercise sheet (string)
        :param model: the name of the model (string)
        :param index: the solution index to mark as filled
        :param num_of_solutions: the number of solutions for the given model (int). Default is 1.
        :return: True if successful, False if solutions can't be added (e.g. maximum exceeded)
        """

        if not self.idCheck(id) or not self.modelCheck(model):
            print(f"{bcolors.WARNING}Can't add solutions!{bcolors.ENDC}")
            raise ValueError("Wrong arguments! Either id or model is not correct!")

        if not force:
            if self.getStatus(id) == Status.unavailable.value:
                print(
                    f"{bcolors.WARNING}Can't add solutions! The id ({id}) is `{Status.unavailable.value}`!{bcolors.ENDC}"
                )
                raise ValueError("Wrong arguments! Unavailable id!")

        current_num = self.object[id][Keys.solutions.value][model]

        if not force and current_num + num_of_solutions > self.max_sols:
            print(
                f"{bcolors.WARNING}Can't add that many solutions! Maximum exceeded!"
                f"\nMaximum: {self.max_sols}"
                f"\nCurrent: {current_num}"
                f"\nTried to add: {num_of_solutions}{bcolors.ENDC}"
            )
            raise ValueError("Maximum number of solutions exceeded!")

        num_of_solutions = (
            -current_num if current_num + num_of_solutions < 0 else num_of_solutions
        )

        # --- Index-based solution tracking ---
        if model not in self.object[id][Keys.sol_indices.value]:
            self.object[id][Keys.sol_indices.value][model] = []

        # Mark the index as filled if provided
        if index is not None:
            self.object[id][Keys.sol_indices.value][model].append(index)

        self.object[id][Keys.solutions.value][model] += num_of_solutions
        self.object[Keys.meta.value][Keys.total_sols.value] += num_of_solutions
        self.object[Keys.meta.value][
            f"{model}{Keys.model_sols_postfix.value}"
        ] += num_of_solutions

        self.updateStatus(id)
        return True

    def getExistingSolutionIndices(self, id: str, model: str) -> list:
        """
        Returns a list of existing solution indices for a given id/model.
        """
        if not self.idCheck(id) or not self.modelCheck(model):
            return []
        if (
            Keys.sol_indices in self.object[id]
            and model in self.object[id][Keys.sol_indices]
        ):
            return self.object[id][Keys.sol_indices][model]
        return []

    def getNextAvailableSolutionIndex(self, id: str, model: str) -> int:
        """
        Returns the lowest available solution index for a given id/model.
        """
        existing = self.getExistingSolutionIndices(id, model)
        if len(existing) > 20:
            existing = set(existing)  # Convert to set for faster lookup

        for i in range(self.max_sols):
            if i not in existing:
                return i
        return None

    def setSolutionNumber(self, id: str, model: str, num_of_solutions: int = 1) -> bool:
        """
        Sets the number of solutions for the given model in the given id exercise sheet to the specified number.
        :param id: The id of the exercise sheet (string)
        :param model: the name of the model (string)
        :param num_of_solutions: the number of solutions for the given model (int). Default is 1.
        """

        if not self.idCheck(id) or not self.modelCheck(model):
            print(f"{bcolors.WARNING}Can't add solutions!{bcolors.ENDC}")
            raise ValueError("Wrong arguments! Either id or model is not correct!")

        before = self.object[id][Keys.solutions.value][model]

        difference = num_of_solutions - before
        self.addSolution(id, model, difference, force=True)
        return True

    def getStatus(self, id: str) -> str:
        """
        Returns the status of the specified id.
        :param id: The id sheet id (string)
        :return: status ("unavailable", "ready", "in_progress", "done")
        """
        if not self.idCheck(id):
            raise Exception(f"Can't get status! Wrong id given: {id}")

        return self.object[id][Keys.status.value]

    def getTotalSolutionNumber(self, model: str = None) -> int:
        """
        Returns the total number of solutions for the given model or if not given: the total number of solutions for all models.
        :param model: Model name (string)
        :return: Number of solutions for a given or all models.
        """
        if model is None:
            return self.object[Keys.meta.value][Keys.total_sols.value]
        if not self.modelCheck(model):
            raise Exception(
                f"Wrong argument given. Model name given: {model} does not exist!"
            )
        return self.object[Keys.meta.value][f"{model}{Keys.model_sols_postfix.value}"]

    def getSolutionNumber(self, id: str, model: str = None):
        """
        Returns the number of solutions in the given id for the given model (integer) or if not given: the number of solutions for all models (i.e. a dictionary).

        :param id: The ID of the exercise sheet (string)
        :param model: The name of the model (string). Optional.
        :return: Number of solutions for a given model (integer) or if the model is not given, a dict with the numbers of solutions for all models.
        """

        if not self.idCheck(id):
            print(f"{bcolors.WARNING}Can't get solutions number!{bcolors.ENDC}")
            raise Exception("Wrong arguments!")

        if model is None:
            return self.object[id][Keys.solutions.value]

        if not self.modelCheck(model):
            raise Exception("Wrong arguments!")
        return self.object[id][Keys.solutions.value][model]

    def updateStatus(self, id: str, status: str = None) -> bool:
        """
        If status is provided, changes a specified id sheet status to the specified status.

        Else (if no status provided):
        Checks the given id sheet (number of solutions). Updates the status of the sheet accordingly to the number of solutions per model.
        E.g.:

        - if the status of exercise sheet was previously "unavailable", it is kept as it is and a warning is printed.

        - if all models have maximum number of solutions, then it changes the status to "done"

        - if there are no solutions, it is set to "ready".

        - if there are some solutions, it changes it to "in_progress"

        :param id: The id sheet id (string)
        :param status: The new status to update the sheet with. Optional. Different functionality if omitted. Possible values: "unavailable", "ready", "in_progress", "done".
        :return: True if successfully performed. False otherwise.
        """

        if status is not None:

            if not self.idCheck(id):
                print(f"{bcolors.WARNING}Can't change status!{bcolors.ENDC}")
                raise Exception("Wrong arguments! Wrong ID!")

            if status not in JSONmanager.STATUS:
                print(
                    f"{bcolors.WARNING}Failed to change the status! '{status}' No such status exists!{bcolors.ENDC}"
                )
                raise Exception("Wrong arguments! Incorrect status given!")

            self.object[id][Keys.status.value] = status
            return True

        else:
            all_equal = True
            all_zero = True
            all_over = True
            in_progress = False
            over = False

            sols = self.max_sols

            for model in self.models:
                model_sols = self.getSolutionNumber(id, model)

                if model_sols != sols:
                    all_equal = False
                if model_sols != 0:
                    all_zero = False
                if model_sols <= sols:
                    all_over = False
                if model_sols > 0:
                    in_progress = True
                if model_sols > sols:
                    over = True

            if over:
                print(
                    f"{bcolors.WARNING}WARNING! Too many solutions in some IDs!{bcolors.ENDC}"
                )

            if self.getStatus(id) == Status.unavailable.value:
                # "unavailable" status remains the same. Has to be changed manually.
                pass
            elif all_equal:
                self.updateStatus(id, Status.done.value)
            elif all_zero:
                self.updateStatus(id, Status.ready.value)
            elif all_over:
                self.updateStatus(id, Status.over.value)
            elif in_progress:
                self.updateStatus(id, Status.in_progress.value)
            else:
                print(
                    "Something went wrong in ID Status update! Possibly negative values!"
                )
                print(f"ID: {id}")
                return False
            return True

    def needMoreSolutions(self, id: str, model: str = None) -> bool:
        """
        Checks if the current number of solutions for the given model is less than the maximum number of solutions per model. If the model
        is not provided, checks the status of the id sheet (i.e. if there are any missing solutions).
        Additionally, gives False if the id is "unavailable".

        :param id: The id sheet id (string)
        :param model: The name of the model (string). Optional.
        :return: True if there are missing solutions, False otherwise.
        """
        status = self.getStatus(id)

        if model is None:
            if status == Status.ready.value or status == Status.in_progress.value:
                return True
            else:
                return False

        if (
            self.getSolutionNumber(id, model) < self.max_sols
            and status != Status.unavailable.value
        ):
            return True
        else:
            print(
                f"{bcolors.WARNING}No more solutions needed for ID: {id} and model: {model}!"
                f"\nStatus: {status}{bcolors.ENDC}"
            )
        return False

    def restrict(self, unavailable_ids: list) -> bool:
        """
        Restricts the exercise sheets by taking the unavailable ids list and setting metadata for them to "unavailable".
        :param unavailable_ids: The list of unavailable/restricted ids.
        :return: True if at least 1 id was restricted, False otherwise.
        """
        flag = False
        for id in unavailable_ids:
            if self.idCheck(id):
                self.updateStatus(id, Status.unavailable.value)
                self.unavailable_ids.add(id)
                flag = True

        return flag

    def addFailure(self, id: str, model: str) -> bool:
        """
        Adds a failure to the meta part of the json file. Specifically, adds a dictionary {"id" : "id001", "model" : "model123"}.
        Can be later used for re-generation or checking what went wrong.
        :param id: The id sheet id (string)
        :param model: The name of the model (string)
        :return: True if successfully added, False otherwise.
        """
        if self.idCheck(id) and self.modelCheck(model):
            self.object[Keys.meta.value][Keys.failures.value].append(
                {
                    Keys.individual_id.value: id,
                    Keys.individual_model.value: model,
                    Keys.timestamp.value: datetime.now().isoformat(),
                }
            )
            return True
        return False

    def monitorFailures(
        self, num_failures: int = 5, interval_seconds: int = 60, strict: bool = False
    ) -> bool:
        """
        Checks if the last N failures happened within interval_seconds.
        In strict mode, raises an exception if so, if not strict - returns False.

        :param num_failures: The number of failures to check (default is 5).
        :param interval_seconds: The time interval in seconds to check the failures (default is 60 seconds).
        :param strict: If True, raises an exception if the condition is met, otherwise returns False.
        :return: True if the condition is met, False otherwise.
        """

        failures = self.object[Keys.meta.value][Keys.failures.value]

        if len(failures) < num_failures:
            return True

        # Get the last N failures
        recent_failures = failures[-num_failures:]
        times = [
            datetime.fromisoformat(f[Keys.timestamp.value]) for f in recent_failures
        ]

        # Checks if all failures happened within the specified interval
        if (times[-1] - times[0]).total_seconds() <= interval_seconds:
            print(f"{bcolors.WARNING} TOO MANY FAILURES DETECTED! {bcolors.ENDC}")

            if strict:
                raise Exception(
                    f"Too many failures: {num_failures} failures within {interval_seconds} seconds."
                )
            else:
                return False
        return True

    def idCheck(self, id: str) -> bool:
        """
        Checks if the given id is valid (i.e. exists in the dictionary).
        :param id: The id to check.
        :return:
        True if it exists in the dictionary, False otherwise.
        """
        if id in self.object.keys():
            return True
        print(f"{bcolors.WARNING}No such ID: `{id}` exists!{bcolors.ENDC}")
        return False

    def modelCheck(self, model: str) -> bool:
        """
        Checks if the given model is valid (i.e. exists in the dictionary).

        :param model: The model to check (string)
        :return:
        True if it exists in the dictionary, False otherwise.
        """
        if model in self.models:
            return True
        print(f"{bcolors.WARNING}No such model: `{model}` exists!{bcolors.ENDC}")
        return False

    @staticmethod
    def idListFromDirectory(directory: str):
        """
        Takes directory as a parameter, iterates through all files there and stores them as ids in a list and returns it.
        The function expects that the ids of the sheets (files) are
        encoded into the filename. E.g. if filenames are 'id0212.txt' and 'id0012', program outputs list ['id0212.txt', 'id0012'].

        :param directory: Directory to read files from.
        :return: List of ids.
        """
        ids_list = []

        for filename in os.listdir(directory):
            id = filename.split(".")[0]  # id0210.html -> id0210
            ids_list.append(id)

        return ids_list

    @staticmethod
    def unavailableIdsFromCSV(filename: str) -> list:
        """
        Reads a CSV file from the given directory and extracts the ids that are marked as unavailable.
        The CSV file is expected to have a column named 'id' with the ids.

        :param filename: Filename/path to read the CSV file from.
        :return: List of unavailable ids.
        """
        unavailable_ids = []
        try:
            with open(filename, "r") as file:
                reader = csv.reader(file)
                header = next(reader)
                for row in reader:
                    id_value = row[0].strip()
                    unavailable_ids.append(id_value)
        except FileNotFoundError:
            raise FileNotFoundError(f"File not found: {filename}")
        return unavailable_ids

    def updateMaxSols(self, new_max_sols: int) -> bool:
        """
        Updates the maximum number of solutions per model in the JSON object.
        :param new_max_sols: The new maximum number of solutions (integer).
        :return: True if successfully updated, False otherwise.
        """
        if new_max_sols < 0:
            print(
                f"{bcolors.WARNING}New maximum number of solutions must be a non-negative integer!{bcolors.ENDC}"
            )
            return False

        self.max_sols = new_max_sols
        for id in self.ids:
            self.updateStatus(id)
        return True

    def reset_model_solutions(self, model: str):
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

    def __str__(self) -> str:
        return json.dumps(self.object, indent=self.DEFAULT_IDENTATION_JSON)
