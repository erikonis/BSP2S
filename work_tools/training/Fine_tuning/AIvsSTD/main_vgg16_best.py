import torch
import torch.nn as nn
import CNN_best_main

from torchvision.models import vgg16, VGG16_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = True
#postfix = "AIvsSTD_raw_sheetSplit" if RAW else "AIvsSTD_preproc_sheetSplit"
postfix = "AIvsSTD_raw" if RAW else "AIvsSTD_preproc"

MODEL_NAME = "vgg16"
MODEL_NAME_FULL = "VGG-16"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
LOAD_MODEL_PATH = "models/best_vgg16_raw_sheetSplit.pth" if RAW else "models/best_vgg16_preproc_sheetSplit.pth"
VAL_SET_SIZE = 600
EPOCHS = 40
BATCH = 32

OUTPUT = f"best_{MODEL_NAME}_{postfix}/"


def load_model(device, weights_path=None):
    model = vgg16(weights=VGG16_Weights.DEFAULT)

    in_features = model.classifier[-1].in_features
    model.classifier[-1] = nn.Linear(in_features, 1)
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
