import torch
import torch.nn as nn
import torch.optim as optim
from torchvision import datasets, models, transforms
from torch.utils.data import DataLoader, random_split
import matplotlib.pyplot as plt
import numpy as np
from sklearn.metrics import (
    confusion_matrix,
    roc_auc_score,
    f1_score,
    precision_score,
    recall_score,
    roc_curve,
    matthews_corrcoef,
)
import seaborn as sns
import time
import os

from torchvision.models import densenet121, DenseNet121_Weights

import EarlyStop


STATS_POSTFIX = "_stats.txt"


def initialize_device():
    """
    Initialize the device for training.

    Returns:
      Either cuda (if available) or cpu
    """

    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    print("Using device:", device)
    return device


def get_data_loaders(
    train_data_path: str, test_data_path: str, validation_set_size: int, batch_size: int
):
    """
    Get data loaders for training, validation, and testing datasets.

    Args:
     train_data_path: path to the train_data_path. Code expends to find 2 folders for each category there.
     test_data_path: path to the test_data_path. Code expends to find 2 folders for each category there.
     validation_set_size: integer. Number of training samples to be used for validation. Preferably 10% of the whole training dataset.
     batch_size: Size of batch. Preferably 32.

    Returns:
        train_loader, val_loader, test_loader
    """

    transform = transforms.Compose(
        [
            transforms.Resize((224, 224)),
            transforms.RandomHorizontalFlip(),
            transforms.RandomRotation(30),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
        ]
    )

    full_train_dataset = datasets.ImageFolder(root=train_data_path, transform=transform)
    test_dataset = datasets.ImageFolder(root=test_data_path, transform=transform)

    val_size = validation_set_size  # 10% of whole dataset
    train_size = (
        len(full_train_dataset) - val_size
    )  # Left with 54208 samples for training

    train_dataset, val_dataset = random_split(
        full_train_dataset, [train_size, val_size]
    )

    train_loader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)
    val_loader = DataLoader(val_dataset, batch_size=batch_size, shuffle=False)
    test_loader = DataLoader(test_dataset, batch_size=batch_size, shuffle=False)

    return train_loader, val_loader, test_loader


def train_model(
    model,
    device,
    train_loader,
    val_loader,
    epochs: int,
    output_dir: str,
    model_name: str,
):
    """
    Train the model with the given parameters.

    Args:
    model: The model to be trained.
    device: The device to train the model on (e.g., "cuda" or "cpu").
    train_loader: DataLoader for the training set.
    val_loader: DataLoader for the validation set.
    epochs: Integer, number of epochs to train the model. Preferably 40.
    output_dir: The directory where the model shall be saved. E.g. "best_model"
    model_name: The name of the model to be saved. E.g. "best_dn121_raw"

    Returns:
        train_losses, train_accuracies, val_losses, val_accuracies, best_epoch (epoch at which model was saved)
    """

    criterion = nn.BCEWithLogitsLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.0001)

    train_losses, train_accuracies = [], []
    val_losses, val_accuracies = [], []

    best_model_path = os.path.join(output_dir, model_name + ".pth")
    best_epoch = 0
    best_val_acc = 0.0
    early_stop = EarlyStop.EarlyStop(
        patience=5, delta=0.0001, verbose=True, path=best_model_path
    )

    for epoch in range(epochs):
        model.train()
        running_loss, correct, total = 0.0, 0, 0
        all_preds, all_labels = [], []

        for inputs, labels in train_loader:
            inputs, labels = inputs.to(device), labels.to(device)
            optimizer.zero_grad()
            outputs = model(inputs)
            loss = criterion(outputs.squeeze(), labels.float())
            loss.backward()
            optimizer.step()

            running_loss += loss.item()
            preds = torch.sigmoid(outputs).squeeze() > 0.5
            correct += (preds == labels).sum().item()
            total += labels.size(0)

            all_preds.extend(preds.cpu().numpy())
            all_labels.extend(labels.cpu().numpy())

        epoch_loss = running_loss / len(train_loader)
        epoch_acc = correct / total * 100
        train_losses.append(epoch_loss)
        train_accuracies.append(epoch_acc)

        # Recall and FPR on training set
        train_recall = recall_score(all_labels, all_preds)
        tn, fp, fn, tp = confusion_matrix(all_labels, all_preds, labels=[0, 1]).ravel()
        train_fpr = fp / (fp + tn) if (fp + tn) > 0 else 0.0

        print(
            f"Epoch {epoch + 1}/{epochs} - Loss: {epoch_loss:.4f} - Accuracy: {epoch_acc:.2f}% - Recall: {train_recall:.3f} - FPR: {train_fpr:.3f}"
        )

        model.eval()
        val_loss, val_correct, val_total = 0.0, 0, 0
        all_val_preds, all_val_labels = [], []

        with torch.no_grad():
            for inputs, labels in val_loader:
                inputs, labels = inputs.to(device), labels.to(device)
                outputs = model(inputs)
                probs = torch.sigmoid(outputs).view(-1)
                loss = criterion(outputs.view(-1), labels.float())
                val_loss += loss.item()
                preds = (probs > 0.5).long()
                val_correct += (preds == labels).sum().item()
                val_total += labels.size(0)

                all_val_preds.extend(preds.cpu().numpy())
                all_val_labels.extend(labels.cpu().numpy())

        val_epoch_loss = val_loss / len(val_loader)
        val_epoch_acc = val_correct / val_total * 100
        val_losses.append(val_epoch_loss)
        val_accuracies.append(val_epoch_acc)

        # Recall and FPR for validation
        val_recall = recall_score(all_val_labels, all_val_preds)
        tn, fp, fn, tp = confusion_matrix(
            all_val_labels, all_val_preds, labels=[0, 1]
        ).ravel()
        val_fpr = fp / (fp + tn) if (fp + tn) > 0 else 0.0

        print(
            f"Validation - Loss: {val_epoch_loss:.4f} - Accuracy: {val_epoch_acc:.2f}% - Recall: {val_recall:.3f} - FPR: {val_fpr:.3f}"
        )

        # Early stopping check
        if not early_stop.early_stop:
            early_stop(val_epoch_loss, model)
            model_save_time = time.time()

        if early_stop.counter == 0:
            best_epoch = epoch + 1
            best_val_acc = val_epoch_acc

    print(f"Best model saved at epoch {best_epoch} with accuracy: {best_val_acc:.2f}%")

    # Creating stats file
    with open(os.path.join(output_dir, model_name + STATS_POSTFIX), "w") as f:
        f.write(
            f"Best model saved at epoch {best_epoch} with accuracy: {best_val_acc:.2f}%\n\n"
        )

    return (
        train_losses,
        train_accuracies,
        val_losses,
        val_accuracies,
        best_epoch,
        model_save_time,
    )


def evaluate_model(
    model,
    test_loader,
    device,
    output_dir: str,
    model_name: str,
    full_model_name: str,
):
    """
    Evaluate the model on the test set and save the results.

    Args:
        model: The model to evaluate.
        test_loader: DataLoader for the test set.
        device: The device to run the evaluation on (e.g., "cuda" or "cpu").
        output_dir: The directory to save the evaluation results.
        model_name: The name of the model.
        full_model_name: The full name of the model.
    """
    test_labels, test_preds, test_probs = [], [], []
    model.eval()
    with torch.no_grad():
        for inputs, labels in test_loader:
            inputs, labels = inputs.to(device), labels.to(device)
            outputs = model(inputs)
            probs = torch.sigmoid(outputs).view(-1)
            preds = (probs > 0.5).long()

            test_labels.extend(labels.cpu().numpy())
            test_preds.extend(preds.cpu().numpy())
            test_probs.extend(probs.cpu().numpy())

    test_labels = np.array(test_labels)
    test_preds = np.array(test_preds)
    test_probs = np.array(test_probs)

    mcc = matthews_corrcoef(test_labels, test_preds)
    f1 = f1_score(test_labels, test_preds)
    recall = recall_score(test_labels, test_preds)
    precision = precision_score(test_labels, test_preds)
    roc_auc = roc_auc_score(test_labels, test_probs)
    test_accuracy = (test_preds == test_labels).sum() / len(test_labels) * 100

    # Save Metrics to a File

    out = os.path.join(output_dir, model_name + STATS_POSTFIX)

    with open(out, "a") as f:
        f.write("\n\n=== METRICS STATS ===\n\n")
        f.write(f"Model: {model_name}\n")
        f.write(f"{'-' * 30}\n")
        f.write(f"ROC AUC: {roc_auc:.4f}\n")
        f.write(f"MCC score: {mcc:.4f}\n")
        f.write(f"F1 Score: {f1:.4f}\n")
        f.write(f"Recall: {recall:.4f}\n")
        f.write(f"Precision: {precision:.4f}\n")
        f.write(f"Accuracy: {test_accuracy:.2f}%\n")
        f.write(f"{'-' * 30}")

    print(f"\nMetrics saved to: {out}")

    # CONFUSION MATRIX
    formatted_accuracy = (
        f"{test_accuracy:.1f}" if test_accuracy % 1 != 0 else f"{int(test_accuracy)}"
    )
    label_color = "#1f77b4"
    cm = confusion_matrix(test_labels, test_preds)
    plt.figure(figsize=(6, 6))
    sns.heatmap(
        cm,
        annot=True,
        fmt="d",
        cmap="Blues",
        xticklabels=["Human", "AI"],
        yticklabels=["Human", "AI"],
        annot_kws={"size": 36},
    )
    plt.xlabel("Predicted", color=label_color, labelpad=-45, fontsize=18)
    plt.ylabel("True", color=label_color, labelpad=-45, fontsize=18)
    plt.tick_params(axis="both", which="major", pad=25, labelsize=24)
    plt.title(f"{full_model_name}\nAccuracy {formatted_accuracy}%", pad=20, fontsize=24)
    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, f"Confusion_Matrix_{model_name}.png"))
    plt.close()

    print(f"Test Accuracy: {test_accuracy:.2f}%")
    print(f"F1 Score: {f1:.4f}")
    print(f"Recall: {recall:.4f}")
    print(f"Precision: {precision:.4f}")
    print(f"ROC AUC: {roc_auc:.4f}")

    # ROC
    fpr, tpr, _ = roc_curve(test_labels, test_probs)
    plt.figure(figsize=(6, 6))
    plt.plot(fpr, tpr, label=f"AUC = {roc_auc:.4f}")
    plt.plot([0, 1], [0, 1], "k--")
    plt.xlabel("False Positive Rate")
    plt.ylabel("True Positive Rate")
    plt.title("ROC Curve")
    plt.legend()
    plt.savefig(os.path.join(output_dir, f"ROC_Curve_{model_name}.png"))
    plt.close()

    # Save confidence statistics
    save_confidence_statistics(
        test_probs, test_labels, test_preds, model_name, output_dir
    )


def save_confidence_statistics(
    test_probs, test_labels, test_preds, model_name: str, output_dir: str
):
    """Save confidence statistics in a formatted table"""
    # Get statistics for each class
    student_stats = get_class_confidence_stats(
        test_probs, test_labels, test_preds, class_label=0
    )
    ai_stats = get_class_confidence_stats(
        test_probs, test_labels, test_preds, class_label=1
    )

    # Create formatted output
    header = f"{'Model':<22} {'Label':<8} {'Samples':<8} {'Average Confidence':<18} {'Max Confidence':<14} {'Min Confidence':<14}"
    separator = "-" * len(header)

    student_line = f"{model_name:<22} {'Student':<8} {student_stats['samples']:<8} {student_stats['avg_confidence']:<18.3f} {student_stats['max_confidence']:<14.3f} {student_stats['min_confidence']:<14.3f}"
    ai_line = f"{model_name:<22} {'AI':<8} {ai_stats['samples']:<8} {ai_stats['avg_confidence']:<18.3f} {ai_stats['max_confidence']:<14.3f} {ai_stats['min_confidence']:<14.3f}"

    # Save to file
    conf_stats_path = os.path.join(output_dir, model_name + STATS_POSTFIX)
    with open(conf_stats_path, "a") as f:
        f.write("\n\n=== CONFIDENCE STATISTICS ===\n\n")
        f.write(header + "\n")
        f.write(separator + "\n")
        f.write(student_line + "\n")
        f.write(ai_line + "\n\n")
        f.write(
            "Table 6: Confidence Score Statistics for model acting as oracle on the accurately sorted test codes"
        )

    print(f"\nConfidence statistics saved to: {conf_stats_path}")
    return student_stats, ai_stats


def get_class_confidence_stats(probs, labels, pred_labels, class_label):
    """Calculate confidence statistics for correctly classified samples of a given class"""
    correct_mask = (labels == class_label) & (pred_labels == labels)

    if class_label == 1:
        class_probs = probs[correct_mask]
    else:
        class_probs = 1 - probs[correct_mask]

    if len(class_probs) == 0:
        return {
            "samples": 0,
            "avg_confidence": float(
                np.nan
            ),  # or 0.0 but with a note about no correct predictions
            "max_confidence": float(np.nan),
            "min_confidence": float(np.nan),
        }

    return {
        "samples": len(class_probs),
        "avg_confidence": float(np.mean(class_probs)),
        "max_confidence": float(np.max(class_probs)),
        "min_confidence": float(np.min(class_probs)),
    }


def plot_metrics(
    train_accuracies,
    val_accuracies,
    train_losses,
    val_losses,
    epochs: int,
    best_epoch: int,
    output_dir: str,
    model_name: str,
    full_model_name: str,
):
    """
    Plot training and validation metrics (accuracy and loss) over epochs.
    :param train_accuracies: from train_model
    :param val_accuracies: from train_model
    :param train_losses: from train_model
    :param val_losses: from train_model
    :param epochs: integer. Preferably 40.
    :param best_epoch: From train_model.
    :param output_dir: The directory to save the plotted image. E.g. "model"
    :param model_name: The name of the model to be put in the file name. E.g. "dn121"
    :param full_model_name: The full name of the model to be put as title on the plot. E.g. "SomeModel-123"
    """

    plt.figure(figsize=(12, 5))
    plt.suptitle(full_model_name, fontsize=18)
    plt.subplot(1, 2, 1)
    plt.plot(range(1, epochs + 1), train_accuracies, label="Train Accuracy")
    plt.plot(range(1, epochs + 1), val_accuracies, label="Validation Accuracy")
    plt.axvline(
        best_epoch, color="red", linestyle="--", label="Best Epoch"
    )  # Add vertical lin
    plt.xlabel("Epoch")
    plt.ylabel("Accuracy")
    if epochs >= 10:
        plt.xticks(range(0, epochs + 1, 5))
    else:
        plt.xticks(range(1, epochs + 1))
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.plot(range(1, epochs + 1), train_losses, label="Train Loss")
    plt.plot(range(1, epochs + 1), val_losses, label="Validation Loss")
    plt.axvline(
        best_epoch, color="red", linestyle="--", label="Best Epoch"
    )  # Add vertical line
    plt.xlabel("Epoch")
    plt.ylabel("Loss")
    if epochs >= 10:
        plt.xticks(range(0, epochs + 1, 5))
    else:
        plt.xticks(range(1, epochs + 1))
    plt.legend()

    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, f"Loss_Accuracy_vs_epochs_{model_name}.png"))
    plt.close()


# Unusable with EarlyStopping
def save_model(model, output_dir: str, model_name: str):
    path = os.path.join(output_dir, f"{model_name}_bin_classifier.pth")
    torch.save(model.state_dict(), path)
    print("Model saved successfully!")
    return path


def save_training_time(start_time, model_save_time, output_dir: str, model_name: str):
    """
    Saved the total training time to a file.
    :param start_time: from time.time() at the beginning of the training
    :param output_dir: the directory where the training time file shall be saved. E.g. "best_model"
    :param model_name: the name of the model to be saved. E.g. "dn121"
    :param prefix: the prefix to be added to the model name. E.g. "raw" or "preprocessed"
    """

    total_time = time.time() - start_time
    optimal_time = model_save_time - start_time

    print("it took: ", total_time)

    with open(os.path.join(output_dir, model_name + STATS_POSTFIX), "a") as f:
        f.write(f"\n\n=== TRAINING TIME ===\n\n")
        f.write(f"Total training time: {total_time:.2f} seconds\n")
        f.write(f"Optimal training time: {optimal_time:.2f} seconds")


def training_cycle(
    output: str,
    postfix: str,
    model: nn.Module,
    device: torch.device,
    model_name: str,
    full_model_name: str,
    train_data_path: str,
    test_data_path: str,
    validation_set_size: int,
    batch_size: int,
    epochs: int,
):
    """
    This function encapsulates the training process for a CNN model. It performs binary classification, with AI category being the positive class, and Human category being negative (0).

    Args:
    - output: The output directory for saving model checkpoints and logs.
    - postfix: A postfix to distinguish different runs (e.g., "run1", "run2", "raw", "preprocessed").
    - model: The CNN model to be trained.
    - device: The device to train the model on (e.g., "cuda" or "cpu").
    - model_name: The name of the model (e.g., "dn121").
    - full_model_name: The full name of the model (e.g., "DenseNet-121").
    - train_data_path: The file path to the training data.
    - test_data_path: The file path to the test data.
    - validation_data_path: The file path to the validation data.
    - batch_size: The batch size for training.
    - epochs: The number of epochs to train for.
    """

    model_name = f"best_{model_name}_{postfix}"

    start_time = time.time()
    print("Starting...")
    os.makedirs(output, exist_ok=True)

    train_loader, val_loader, test_loader = get_data_loaders(
        train_data_path=train_data_path,
        test_data_path=test_data_path,
        validation_set_size=validation_set_size,
        batch_size=batch_size,
    )
    print("Data loaders created.")

    # Train the model
    (
        train_losses,
        train_accuracies,
        val_losses,
        val_accuracies,
        best_epoch,
        model_save_time,
    ) = train_model(
        model,
        device,
        train_loader,
        val_loader,
        epochs=epochs,
        output_dir=output,
        model_name=model_name,
    )
    print("Model trained.")

    # Plot metrics
    plot_metrics(
        train_accuracies,
        val_accuracies,
        train_losses,
        val_losses,
        epochs=epochs,
        best_epoch=best_epoch,
        output_dir=output,
        model_name=model_name,
        full_model_name=full_model_name,
    )
    print("Metrics plotted.")

    # Load the best model for evaluation
    best_model_path = os.path.join(output, model_name + ".pth")

    print("Loading the best model for evaluation...")
    model.load_state_dict(torch.load(best_model_path))
    print("Best model loaded successfully.")

    # Evaluate the model
    evaluate_model(model, test_loader, device, output, model_name, full_model_name)
    print("Model evaluated.")

    # Save training time
    save_training_time(start_time, model_save_time, output, model_name)
    print("Training time saved.")

    print("Finished training process.")
