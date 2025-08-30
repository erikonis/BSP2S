import torch.nn as nn
import CNN_best_main

from torchvision.models import vgg16, VGG16_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = True
postfix = "raw" if RAW else "preprocessed"

MODEL_NAME = "vgg16"
MODEL_NAME_FULL = "VGG-16"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
VAL_SET_SIZE = 6776
EPOCHS = 40
BATCH = 32

OUTPUT = f"best_{MODEL_NAME}_{postfix}/"


def load_model(device):
    model = vgg16(weights=VGG16_Weights.DEFAULT)

    in_features = model.classifier[-1].in_features
    model.classifier[-1] = nn.Linear(in_features, 1)
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
