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
    roc_curve
)
import seaborn as sns
import time
import os

from torchvision.models import densenet121, DenseNet121_Weights

import EarlyStop


# GLOBAL VARIABLES
raw = True
model_name = "dn121"
model_name_full = "DenseNet-121"
prefix = "raw" if raw else "preprocessed"
output = f"best_scratch_{model_name}_{prefix}/"
EPOCHS = 40
BATCH = 32


def initialize_device():
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    print("Using device:", device)
    return device


def load_model(device):
    model = densenet121(weights=None)
    model.classifier = nn.Linear(model.classifier.in_features, 1)
    return model.to(device)


def get_data_loaders():
    transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.RandomHorizontalFlip(),
        transforms.RandomRotation(30),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    full_train_dataset = datasets.ImageFolder(root=f'../data_{prefix}/train', transform=transform)
    test_dataset = datasets.ImageFolder(root=f'../data_{prefix}/test', transform=transform)

    val_size = 6776 #10% of whole dataset
    train_size = len(full_train_dataset) - val_size #Left with 54208 samples for training

    train_dataset, val_dataset = random_split(full_train_dataset, [train_size, val_size])

    train_loader = DataLoader(train_dataset, batch_size=BATCH, shuffle=True)
    val_loader = DataLoader(val_dataset, batch_size=BATCH, shuffle=False)
    test_loader = DataLoader(test_dataset, batch_size=BATCH, shuffle=False)

    return train_loader, val_loader, test_loader


def train_model(model, device, train_loader, val_loader, epochs):
    criterion = nn.BCEWithLogitsLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.0001)

    train_losses, train_accuracies = [], []
    val_losses, val_accuracies = [], []

    best_model_path = f"{output}best_model_{model_name}_{prefix}.pth"
    best_epoch = 0
    best_val_acc = 0.0
    early_stop = EarlyStop.EarlyStop(patience=5, delta=0.0001, verbose=True, path=best_model_path)

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

        print(f"Epoch {epoch + 1}/{epochs} - Loss: {epoch_loss:.4f} - Accuracy: {epoch_acc:.2f}% - Recall: {train_recall:.3f} - FPR: {train_fpr:.3f}")

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
        tn, fp, fn, tp = confusion_matrix(all_val_labels, all_val_preds, labels=[0, 1]).ravel()
        val_fpr = fp / (fp + tn) if (fp + tn) > 0 else 0.0

        print(f"Validation - Loss: {val_epoch_loss:.4f} - Accuracy: {val_epoch_acc:.2f}% - Recall: {val_recall:.3f} - FPR: {val_fpr:.3f}")

        # Early stopping check
        if not early_stop.early_stop:
            early_stop(val_epoch_loss, model)

        if early_stop.counter == 0:
            best_epoch = epoch + 1
            best_val_acc = val_epoch_acc

    print(f"Best model saved at epoch {best_epoch} with accuracy: {best_val_acc:.2f}%")
    with open(f"{output}best_epoch_{model_name}_{prefix}.txt", "w") as f:
        f.write(f"Best model saved at epoch {best_epoch} with accuracy: {best_val_acc:.2f}%")

    return train_losses, train_accuracies, val_losses, val_accuracies, best_epoch



def plot_metrics(train_accuracies, val_accuracies, train_losses, val_losses, epochs, best_epoch):
    plt.figure(figsize=(12, 5))
    plt.suptitle(model_name_full, fontsize=18)
    plt.subplot(1, 2, 1)
    plt.plot(range(1, epochs + 1), train_accuracies, label='Train Accuracy')
    plt.plot(range(1, epochs + 1), val_accuracies, label='Validation Accuracy')
    plt.axvline(best_epoch, color='red', linestyle='--', label='Best Epoch')  # Add vertical lin
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    if epochs >= 10:
        plt.xticks(range(0, epochs + 1, 5))
    else:
        plt.xticks(range(1, epochs + 1))
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.plot(range(1, epochs + 1), train_losses, label='Train Loss')
    plt.plot(range(1, epochs + 1), val_losses, label='Validation Loss')
    plt.axvline(best_epoch, color='red', linestyle='--', label='Best Epoch')  # Add vertical line
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    if epochs >= 10:
        plt.xticks(range(0, epochs + 1, 5))
    else:
        plt.xticks(range(1, epochs + 1))
    plt.legend()

    plt.tight_layout()
    plt.savefig(f"{output}Loss_Accuracy_vs_epochs_{model_name}_{prefix}.png")
    plt.close()


def evaluate_model(model, test_loader, device):
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

    f1 = f1_score(test_labels, test_preds)
    recall = recall_score(test_labels, test_preds)
    precision = precision_score(test_labels, test_preds)
    roc_auc = roc_auc_score(test_labels, test_probs)
    test_accuracy = (test_preds == test_labels).sum() / len(test_labels) * 100

    # Save Metrics to a File
    metrics_output_path = f"{output}metrics_{model_name}_{prefix}.txt"
    with open(metrics_output_path, "w") as f:
        f.write(f"Model: {model_name}\n")
        f.write(f"Dataset: {prefix}\n")
        f.write(f"{'-' * 30}\n")
        f.write(f"F1 Score: {f1:.4f}\n")
        f.write(f"Recall: {recall:.4f}\n")
        f.write(f"Precision: {precision:.4f}\n")
        f.write(f"Accuracy: {test_accuracy:.2f}%\n")
        f.write(f"{'-' * 30}\n")

    print(f"\nMetrics saved to: {metrics_output_path}")

    # CONFUSION MATRIX
    formatted_accuracy = f"{test_accuracy:.1f}" if test_accuracy % 1 != 0 else f"{int(test_accuracy)}"
    label_color = '#1f77b4'
    cm = confusion_matrix(test_labels, test_preds)
    plt.figure(figsize=(6, 6))
    sns.heatmap(cm, annot=True, fmt="d", cmap="Blues", xticklabels=["Human", "AI"], yticklabels=["Human", "AI"],
                annot_kws={"size": 36})
    plt.xlabel('Predicted', color=label_color, labelpad=-45, fontsize=18)
    plt.ylabel('True', color=label_color, labelpad=-45, fontsize=18)
    plt.tick_params(axis='both', which='major', pad=25, labelsize=24)
    plt.title(f"{model_name_full}\nAccuracy {formatted_accuracy}%", pad=20, fontsize=24)
    plt.tight_layout()
    plt.savefig(f"{output}Confusion_Matrix_{model_name}_{prefix}.png")
    plt.close()

    print(f"Test Accuracy: {test_accuracy:.2f}%")
    print(f"F1 Score: {f1:.4f}")
    print(f"Recall: {recall:.4f}")
    print(f"Precision: {precision:.4f}")
    print(f"ROC AUC: {roc_auc:.4f}")

    # ROC
    fpr, tpr, _ = roc_curve(test_labels, test_probs)
    plt.figure(figsize=(6, 6))
    plt.plot(fpr, tpr, label=f'AUC = {roc_auc:.4f}')
    plt.plot([0, 1], [0, 1], 'k--')
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title('ROC Curve')
    plt.legend()
    plt.savefig(f"{output}ROC_Curve_{model_name}_{prefix}.png")
    plt.close()



    # Save confidence statistics
    save_confidence_statistics(
        test_probs, test_labels, test_preds
    )


def get_class_confidence_stats(probs, labels, pred_labels, class_label):
    """Calculate confidence statistics for correctly classified samples of a given class"""
    correct_mask = (labels == class_label) & (pred_labels == labels)

    if class_label == 1:
        class_probs = probs[correct_mask]
    else:
        class_probs = 1 - probs[correct_mask]

    if len(class_probs) == 0:
        return {
            'samples': 0,
            'avg_confidence': float(np.nan),  # or 0.0 but with a note about no correct predictions
            'max_confidence': float(np.nan),
            'min_confidence': float(np.nan)
        }

    return {
        'samples': len(class_probs),
        'avg_confidence': float(np.mean(class_probs)),
        'max_confidence': float(np.max(class_probs)),
        'min_confidence': float(np.min(class_probs))
    }


def save_confidence_statistics(test_probs, test_labels, test_preds):
    """Save confidence statistics in a formatted table"""
    # Get statistics for each class
    student_stats = get_class_confidence_stats(test_probs, test_labels, test_preds, class_label=0)
    ai_stats = get_class_confidence_stats(test_probs, test_labels, test_preds, class_label=1)

    # Create formatted output
    header = f"{'Model':<10} {'Dataset':<12} {'Label':<8} {'Samples':<8} {'Average Confidence':<18} {'Max Confidence':<14} {'Min Confidence':<14}"
    separator = "-" * len(header)

    student_line = f"{model_name:<10} {prefix:<12} {'Student':<8} {student_stats['samples']:<8} {student_stats['avg_confidence']:<18.3f} {student_stats['max_confidence']:<14.3f} {student_stats['min_confidence']:<14.3f}"
    ai_line = f"{model_name:<10} {prefix:<12} {'AI':<8} {ai_stats['samples']:<8} {ai_stats['avg_confidence']:<18.3f} {ai_stats['max_confidence']:<14.3f} {ai_stats['min_confidence']:<14.3f}"

    # Save to file
    conf_stats_path = f"{output}confidence_statistics_{model_name}_{prefix}.txt"
    with open(conf_stats_path, "w") as f:
        f.write(header + "\n")
        f.write(separator + "\n")
        f.write(student_line + "\n")
        f.write(ai_line + "\n\n")
        f.write("Table 6: Confidence Score Statistics for model acting as oracle on the accurately sorted test codes")

    print(f"\nConfidence statistics saved to: {conf_stats_path}")
    return student_stats, ai_stats


def save_model(model):
    path = f"{output}{model_name}_binary_classifier_{prefix}.pth"
    torch.save(model.state_dict(), path)
    print("Model saved successfully!")
    return path


def save_training_time(start_time):
    total_time = time.time() - start_time
    print("it took: ", total_time)
    with open(f"{output}training_time_{model_name}_{prefix}.txt", "w") as f:
        f.write(f"Total training time: {total_time:.2f} seconds")

def main():
    print("Entered main function. Starting training process.")
    start_time = time.time()
    print("start time: ", start_time)
    epochs = EPOCHS
    os.makedirs(output, exist_ok=True)
    print("Made output directory: ", output)

    # Initialize device and model
    device = initialize_device()
    load_start = time.time()
    model = load_model(device)
    finish_load = time.time()
    print(f"model loaded in: {finish_load - load_start}")

    # Load data
    train_loader, val_loader, test_loader = get_data_loaders()
    print("data loaded")

    # Train the model
    train_losses, train_accuracies, val_losses, val_accuracies, best_epoch = train_model(model, device, train_loader,
                                                                                         val_loader,
                                                                                         epochs)
    print("trained model")

    # Plot metrics
    plot_metrics(train_accuracies, val_accuracies, train_losses, val_losses, epochs, best_epoch)
    print("metrics plotted")

    # Load the best model for evaluation
    best_model_path = f"{output}best_model_{model_name}_{prefix}.pth"
    print("Loading the best model for evaluation...")
    model.load_state_dict(torch.load(best_model_path))
    print("Best model loaded successfully")

    # Evaluate the model
    evaluate_model(model, test_loader, device)
    print("model evaluated")

    # Save training time
    save_training_time(start_time)
    print("training time saved")

    print("Finished training process.")


if __name__ == "__main__":
    print("Starting...", flush=True)
    main()
