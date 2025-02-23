package com.example;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.StreamingChatResponseMessageUpdate;
import com.azure.core.credential.AzureKeyCredential;

import java.util.ArrayList;
import java.util.List;

/*
 * Azure AI Inference Chat Stream Sample
 *
 * Imports:
 *   Import necessary classes from the Azure AI Inference SDK and Azure core libraries.
 *
 * Environment Variables:
 *   Retrieve AZURE_OPENAI_ENDPOINT and AZURE_OPENAI_API_KEY for configuration.
 *
 * Client Initialization:
 *   Build a ChatCompletionsClient using ChatCompletionsClientBuilder with AzureKeyCredential and endpoint.
 *
 * Message Preparation:
 *   Prepare a list of chat messages with a system message and a user message.
 *   System message suggests a json format for responses.
 *   User message is a riddle "Maria's father has 4 daughters: Spring, Autumn, Winter. What is the name of the fourth daughter?".
 *
 * Options Setup:
 *   Create a ChatCompletionsOptions object with the messages and set the model to 'DeepSeek-R1'.
 *
 * Streaming API Call and Processing:
 *   Invoke the streaming chat completions client.completeStream(options).stream().forEach to process updates.
 *   For each update, check if choices are available and retrieve the delta from the first choice.
 *     - Print the role on a new line if available.
 *     - Append the content (without an extra newline) for human-readable output.
 */
public final class BasicChatStreamSample {
    public static void main(String[] args) {
        // Retrieve the endpoint and API key from environment variables.
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");

        // Validate that both environment variables are set.
        if (endpoint == null || apiKey == null) {
            System.err.println("Please set the environment variables AZURE_OPENAI_ENDPOINT and AZURE_OPENAI_API_KEY.");
            System.exit(1);
        }

        // Build the ChatCompletionsClient using the provided endpoint and API key.
        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        // Prepare chat messages: one system message and one user message.
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("system_prompt = \"\"\"\r\n" +
                        "You are an AI that provides information in JSON format.\r\n" +
                        "\r\n" +
                        "EXAMPLE INPUT:\r\n" +
                        "What is the capital of France?\r\n" +
                        "\r\n" +
                        "EXAMPLE JSON OUTPUT:\r\n" +
                        "{\r\n" +
                        "    \"question\": \"What is the capital of France?\",\r\n" +
                        "    \"answer\": \"Paris\"\r\n" +
                        "}\r\n" +
                        "\"\"\"\r\n" +
                        "."));
        
        chatMessages.add(new ChatRequestUserMessage("Maria's father has 4 daughters: Spring, Autumn, Winter. What is the name of the fourth daughter?"));

        // Create ChatCompletionsOptions with the prepared messages and set the model.
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");

        // Process each update in the stream.
        client.completeStream(options).stream().forEach(update -> {
            if (update.getChoices() == null || update.getChoices().isEmpty()) {
                return;
            }
            // Retrieve the delta update from the first choice.
            StreamingChatResponseMessageUpdate delta = update.getChoices().get(0).getDelta();
            if (delta != null) {
                // If the delta contains a role, print it on a new line.
                if (delta.getRole() != null) {
                    System.out.println("Role: " + delta.getRole());
                }
                // Append the delta content if available. we print each delta content without an extra new line.
                if (delta.getContent() != null) {
                    System.out.print(delta.getContent());
                }
            }
        });
    }
}
