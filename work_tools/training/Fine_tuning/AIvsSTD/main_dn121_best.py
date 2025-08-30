import torch
import torch.nn as nn
import CNN_best_main

from torchvision.models import densenet121, DenseNet121_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = True
#postfix = "AIvsSTD_raw_sheetSplit" if RAW else "AIvsSTD_preproc_sheetSplit"
postfix = "AIvsSTD_raw" if RAW else "AIvsSTD_preproc"

MODEL_NAME = "dn121"
MODEL_NAME_FULL = "DenseNet-121"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
LOAD_MODEL_PATH = "models/best_dn121_raw_sheetSplit.pth" if RAW else "models/best_dn121_preproc_sheetSplit.pth"
VAL_SET_SIZE = 600
EPOCHS = 40
BATCH = 32

OUTPUT = f"best_{MODEL_NAME}_{postfix}/"


def load_model(device, weights_path=None):
    model = densenet121(weights=DenseNet121_Weights.DEFAULT)
    model.classifier = nn.Linear(model.classifier.in_features, 1)
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
