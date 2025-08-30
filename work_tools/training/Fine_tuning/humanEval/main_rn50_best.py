import torch
import torch.nn as nn
import CNN_best_main

from torchvision.models import resnet50, ResNet50_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = False
postfix = "humanEval_unique_raw" if RAW else "humanEval_unique_preproc"
#postfix = "AIvsSTD_raw" if RAW else "AIvsSTD_preproc"

MODEL_NAME = "rn50"
MODEL_NAME_FULL = "ResNet-50"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
LOAD_MODEL_PATH = "models/best_rn50_raw_sheetSplit.pth" if RAW else "models/best_rn50_preproc_sheetSplit.pth"
VAL_SET_SIZE = 40
EPOCHS = 40
BATCH = 16

OUTPUT = f"best_{MODEL_NAME}_{postfix}/"


def load_model(device, weights_path=None):
    model = resnet50(weights=ResNet50_Weights.DEFAULT)
    model.fc = nn.Linear(model.fc.in_features, 1)
    model = model.to(device)
    if weights_path:
        model.load_state_dict(torch.load(weights_path, map_location=device))
    return model


def main():

    # Initialize device and model
    device = CNN_best_main.initialize_device()
    model = load_model(device, LOAD_MODEL_PATH)

    # TRAIN
    CNN_best_main.training_cycle(
        output=OUTPUT,
        postfix=postfix,
        model=model,
        device=device,
        full_model_name=MODEL_NAME_FULL,
        model_name=MODEL_NAME,
        epochs=EPOCHS,
        batch_size=BATCH,
        train_data_path=TRAIN_DATA_PATH,
        test_data_path=TEST_DATA_PATH,
        validation_set_size=VAL_SET_SIZE,
    )


if __name__ == "__main__":
    main()
