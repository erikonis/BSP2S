"""
Models.py

This module defines specific subclasses of the base Model class for various large language models (LLMs) used in code generation.

Key Features:
- Provides wrappers for Google Gemini, OpenAI ChatGPT, Anthropic Claude, Deepseek, and a generic TestModel.
- Each class sets up the correct model name and API configuration for use with OpenRouter.
- Designed for easy integration with solution generation workflows.

Usage:
- Instantiate the desired model class with an API key and optional token limit.
- Use the model instance to generate code solutions via inherited methods.
"""

from .LLM_Model import Model

DEFAULT_TOKENS = 8000


class GeminiModel(Model):
    class_name = "GeminiModel"
    model_name = "gemini"

    def __init__(self, API_KEY, max_tokens: int = DEFAULT_TOKENS):
        """
        Initializes a Google Gemini model Gemini 2.5 Pro from OpenRouter website.
        :param API_KEY: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        """
        super().__init__(
            API_KEY, max_tokens, "google/gemini-2.5-flash", GeminiModel.model_name
        )


class ChatGPTModel(Model):
    class_name = "ChatGPTModel"
    model_name = "chatgpt"

    def __init__(self, API_KEY, max_tokens: int = DEFAULT_TOKENS):
        """
        Initializes a openai gpt-4.1 from OpenRouter website.
        :param API_KEY: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        """
        super().__init__(API_KEY, max_tokens, "openai/gpt-4.1", ChatGPTModel.model_name)


class ClaudeModel(Model):
    class_name = "ClaudeModel"
    model_name = "claude"

    def __init__(self, API_KEY, max_tokens: int = DEFAULT_TOKENS):
        """
        Initializes a Claude model Claude-4-Sonnet from OpenRouter website.
        :param API_KEY: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        """
        super().__init__(
            API_KEY, max_tokens, "anthropic/claude-sonnet-4", ClaudeModel.model_name
        )


class DeepseekModel(Model):
    class_name = "DeepseekModel"
    model_name = "deepseek"

    def __init__(self, API_KEY, max_tokens: int = DEFAULT_TOKENS):
        """
        Initializes a Deepseek model deepseek-chat-v3-0324 from OpenRouter website.
        :param API_KEY: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        """
        super().__init__(
            API_KEY,
            max_tokens,
            "deepseek/deepseek-chat-v3-0324",
            DeepseekModel.model_name,
        )


class TestModel(Model):
    class_name = "TestModel"
    model_name = "test"

    def __init__(
        self,
        API_KEY,
        max_tokens: int = DEFAULT_TOKENS,
        model: str = "mistralai/mistral-small-3.2-24b-instruct-2506:free",
        model_N: str = "test",
    ):
        """
        :param API_KEY: the key from OpenRouter website
        :param max_tokens: max number of tokens needed to solve the problem
        :param model: model name as copied from the OpenRouter website.
        """
        super().__init__(API_KEY, max_tokens, model, model_N)
