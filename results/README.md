# EXPLANATIONS
Considered models are these CNNs: DenseNet121 (dn121), ResNet50 (rn50) and VGG16 (vgg16).

**AIvsSTD** - CNN models were pretrained on our CodeNet based dataset and fine-tuned to AIvsSTD dataset described in [BSP2](https://github.com/erikonis/BSP2).
**humanEval** - CNN models were pretrained on our CodeNet based dataset and fine-tuned to humanEval partition from [Empirical AI code detection study](https://github.com/mahantaf/AI-Detector/tree/master/src/astnn/classification/java/data).
**multiclass** - CNN models were trained to classify a given code's representation among 5 categories: Human, GPT4.1 (chatgpt), Claude Sonnet 4 (sonnet), Gemini 2.5 Flash (gemini) and DeepSeek V3 0324 (deepseek).
**scratch** - CNN models were trained on our CodeNet database from scratch.
**sheetSplit** - CNN models pretrained on ImageNet were trained on our CodeNet database. Test set and train/validation sets split by sheets to avoid duplicates. 
