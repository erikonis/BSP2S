import torch.nn as nn
import CNN_best_main

from torchvision.models import resnet50, ResNet50_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = True
postfix = "raw" if RAW else "preprocessed"

MODEL_NAME = "rn50"
MODEL_NAME_FULL = "ResNet-50"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
VAL_SET_SIZE = 6776
EPOCHS = 40
BATCH = 32

OUTPUT = f"best_{MODEL_NAME}_{postfix}/"


def load_model(device):
    model = resnet50(weights=ResNet50_Weights.DEFAULT)
    model.fc = nn.Linear(model.fc.in_features, 1)
    return model.to(device)


def main():

    # Initialize device and model
    device = CNN_best_main.initialize_device()
    model = load_model(device)

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
