# BSP2S
Repository for storing Summer Bachelor Semester Project files of academic year 24/25. It is a continuation and improved version of BSP2 with a way bigger dataset and better implementation. In addition, this project consists of a system designed to automate code generation using LLMs API from OpenRouter with parallel and linear operation modes.

Project outcome is described in ```BSP2S_paper.pdf``` file.

This repository consists of:
1. results - organized by training mode, specifying all the metrics, graphs and matrices for each model and each training setting (raw or preprocessed).
2. work_tools - separated by categories, each consisting of python codes, used in the project to achieve different aims.

In addition, other project-related files that are not in this repository can be accessed following the links:
- [Dataset used for training](https://huggingface.co/datasets/erikonis/BSP2S-dataset)
- [Obtained CNN models (```.pth``` files)](https://huggingface.co/erikonis/BSP2S-models)


## Related:
[BSP2](https://github.com/erikonis/BSP2) - revious project, which built the foundation for this.

# Explanation

## Brief introduction
This project continued previous BSP and tried to develop CNN model that could classify a given source code file into either AI or Human categories. Current project is only capable of classifying Java programming language files.

## Code To Image
Since CNN models are only capable of reading image data (in particular in our case of dimensions 224x224 pixels), we have to convert the codes into their representation. The workflow is as follows:

1. Java source code file is read in binary mode.
2. We group each 24 bits together (from the start) and interpret the groups as RGB encodings for a pixel (giving 3 channels of 8 bits, capable of encoding 0-255 values). If any bits are missing to construct a group, 0 bits are added to complete.
3. Groups are converted into pixels and an image is constructed pixel by pixel with width determined by file size (Table 1 of ```BSP2S_paper.pdf```).
4. Padding (black pixels) are added if necessary to the end of the last row to make a rectangular image.
5. If the obtained height less than 224px (CNN input dimensions) and less than width, we add padding to make the image rectangular. Otherwise padding is unnecessary.

## Preprocessing
Experiments were performed in 2 settings: in the raw and preprocessed. Raw datasets remain untouched, while preprocessed undergo whitespace normalization (leading and trailing blank space removal, multiple consecutive blank space reduction to 1 and trailing whitespace removal from each line) and total comments removal. Preprocessing is applied to a copy of the original Java source code files dataset and image dataset is constructed in the same manner as described before in **Code To Image**.

## Training
Performed in a few settings with EarlyStopping implementation, stopping training at optimal epoch.

Considered models are these CNNs: DenseNet121 (dn121), ResNet50 (rn50) and VGG16 (vgg16).

### Training variations:
**AIvsSTD** - CNN models were pretrained on our CodeNet based dataset and fine-tuned to AIvsSTD dataset described in [BSP2](https://github.com/erikonis/BSP2).
**humanEval** - CNN models were pretrained on our CodeNet based dataset and fine-tuned to humanEval partition from [Empirical AI code detection study](https://github.com/mahantaf/AI-Detector/tree/master/src/astnn/classification/java/data).
**multiclass** - CNN models were trained to classify a given code's representation among 5 categories: Human, GPT4.1 (chatgpt), Claude Sonnet 4 (sonnet), Gemini 2.5 Flash (gemini) and DeepSeek V3 0324 (deepseek).
**scratch** - CNN models were trained on our CodeNet database from scratch.
**sheetSplit** - CNN models pretrained on ImageNet were trained on our CodeNet database. Test set and train/validation sets split by sheets to avoid duplicates. 
