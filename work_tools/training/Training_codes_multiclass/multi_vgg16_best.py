import torch.nn as nn
import CNN_best_multiclass

from torchvision.models import vgg16, VGG16_Weights

# GLOBAL VARIABLES

# === HYPERPARAMETERS ===
RAW = False
postfix = "raw" if RAW else "preproc"


MODEL_NAME = "vgg16"
MODEL_NAME_FULL = "VGG-16"
TRAIN_DATA_PATH = f"data_{postfix}/train"
TEST_DATA_PATH = f"data_{postfix}/test"
VAL_SET_SIZE = 4235
EPOCHS = 40
BATCH = 32
LABELS = ["human", "chatgpt", "claude", "gemini", "deepseek"]


OUTPUT = f"best_{MODEL_NAME}_{postfix}/"
num_classes = len(LABELS)


def load_model(device):
    model = vgg16(weights=VGG16_Weights.DEFAULT)

    in_features = model.classifier[-1].in_features
    model.classifier[-1] = nn.Linear(in_features, num_classes)
    return model.to(device)


def main():

    # Initialize device and model
    device = CNN_best_multiclass.initialize_device()
    model = load_model(device)

    # TRAIN
    CNN_best_multiclass.training_cycle(
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
        labels=LABELS,
    )


if __name__ == "__main__":
    main()
