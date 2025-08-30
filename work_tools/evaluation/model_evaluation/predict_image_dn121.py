import torch
from torchvision import transforms
from PIL import Image
import sys
import torch.nn as nn
from torchvision.models import densenet121

"""
This script loads a trained and specified model, intakes the image and outputs a model's prediction.
It is designed to work with a binary classification model (e.g., human vs. ai).
Probability of 0 represents human prediction, and 1 represents AI.

Usage (in console):
python predict_image.py <model.pth> <image.jpg>
"""


def load_model(model_path, device):
    model = densenet121(weights=None)  # Do not use pretrained weights
    model.classifier = nn.Linear(model.classifier.in_features, 1)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.eval()
    return model.to(device)

def preprocess_image(image_path):
    transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])
    image = Image.open(image_path).convert('RGB')
    return transform(image).unsqueeze(0)

def predict_image(model, device, image_path):
    input_tensor = preprocess_image(image_path).to(device)
    with torch.no_grad():
        output = model(input_tensor)
        prob = torch.sigmoid(output).item()
        pred = int(prob > 0.5)
    return pred, prob

def main(model_path, image_path):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model = load_model(model_path, device)
    pred, prob = predict_image(model, device, image_path)
    return pred, prob

if __name__ == "__main__":
    # Usage: python predict_single_image.py model.pth image.jpg
    model_path = sys.argv[1]
    image_path = sys.argv[2]
    pred, prob = main(model_path, image_path)
    print(f"Prediction: {pred} (probability: {prob:.4f})")