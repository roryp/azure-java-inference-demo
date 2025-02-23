package com.example;

// Copilot Chat Prompt: "Import all necessary classes from the Azure AI Inference SDK and Azure core libraries."
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.StreamingChatCompletionsUpdate;
import com.azure.ai.inference.models.StreamingChatResponseMessageUpdate;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.IterableStream;

import java.util.ArrayList;
import java.util.List;

/*
 * Explanation of the Annotations
Imports Section:
Copilot Chat Prompt Example:
"Import all necessary classes from the Azure AI Inference SDK and Azure core libraries."
This tells Copilot Chat to include the correct classes.

Environment Variables:
Copilot Chat Prompt Example:
"Retrieve the endpoint and API key from the environment variables AZURE_OPENAI_ENDPOINT and AZURE_OPENAI_API_KEY."
This ensures the program securely gets configuration details.

Client Initialization:
Copilot Chat Prompt Example:
"Build a ChatCompletionsClient using ChatCompletionsClientBuilder with the AzureKeyCredential and endpoint."
This creates a client instance ready to call the API.

Message Preparation:
Copilot Chat Prompt Example:
"Prepare a list of chat messages including a system message and a user message."
This builds the conversation context.

Options Setup:
Copilot Chat Prompt Example:
"Create a ChatCompletionsOptions object with the chat messages and set its model to 'DeepSeek-R1'."
This configures the API call with the required model.

Streaming API Call and Processing:
Copilot Chat Prompt Example:
"Invoke the streaming chat completions method and process each update by checking for choices and printing the role and content if available."
This handles the streaming response.
 */

public final class BasicChatStreamSample {
    public static void main(String[] args) {
        // Copilot Chat Prompt: "Retrieve the endpoint and API key from the environment variables AZURE_OPENAI_ENDPOINT and AZURE_OPENAI_API_KEY."
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");

        // Validate that both environment variables are set.
        if (endpoint == null || apiKey == null) {
            System.err.println("Please set the environment variables AZURE_OPENAI_ENDPOINT and AZURE_OPENAI_API_KEY.");
            System.exit(1);
        }

        // Copilot Chat Prompt: "Build a ChatCompletionsClient using ChatCompletionsClientBuilder with the AzureKeyCredential and endpoint."
        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        // Copilot Chat Prompt: "Prepare a list of chat messages including a system message and a user message."
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant."));
        chatMessages.add(new ChatRequestUserMessage("Maria's father has 4 daughters: Spring, Autumn, Winter. What is the name of the fourth daughter?"));

        // Copilot Chat Prompt: "Create a ChatCompletionsOptions object with the chat messages and set its model to 'DeepSeek-R1'."
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");

        // Copilot Chat Prompt: "Invoke the streaming chat completions method and get an IterableStream of StreamingChatCompletionsUpdate."
        IterableStream<StreamingChatCompletionsUpdate> stream = client.completeStream(options);

        // Copilot Chat Prompt: "Process each streaming update by checking for choices and printing the role and content if available."
        stream.stream().forEach(update -> {
            // Ensure the update has at least one choice.
            if (update.getChoices() == null || update.getChoices().isEmpty()) {
                return;
            }
            // Retrieve the first choice's delta update.
            StreamingChatResponseMessageUpdate delta = update.getChoices().get(0).getDelta();
            if (delta != null) {
                // If the delta contains a role, print it.
                if (delta.getRole() != null) {
                    System.out.println("Role: " + delta.getRole());
                }
                // If the delta contains content, print it.
                if (delta.getContent() != null) {
                    System.out.print(delta.getContent());
                }
            }
        });
    }
}
