package com.example;

import java.util.ArrayList;
import java.util.List;

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

// Create the inference endpoint by following this tutorial:
// https://learn.microsoft.com/en-us/azure/ai-foundry/model-inference/concepts/endpoints
public final class BasicChatStreamSample {
    public static void main(String[] args) {
        // Retrieve the endpoint and API key from environment variables
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");

        // Build the ChatCompletions client.
        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        // Prepare the chat messages.
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant."));
        chatMessages.add(new ChatRequestUserMessage("Maria's father has 4 daughters: Spring, Autumn, Winter. What is the name of the fourth daughter?"));

        // Create the chat completions options and set your model (deployment) name.
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");

        // Invoke the streaming method.
        IterableStream<StreamingChatCompletionsUpdate> stream = client.completeStream(options);

        // Process each streaming update as it arrives.
        stream.stream().forEach(update -> {
            // Check that the update contains at least one choice.
            if (update.getChoices() == null || update.getChoices().isEmpty()) {
                return;
            }
            // Retrieve the first choice's delta update.
            StreamingChatResponseMessageUpdate delta = update.getChoices().get(0).getDelta();
            if (delta != null) {
                if (delta.getRole() != null) {
                    System.out.println("Role: " + delta.getRole());
                }
                if (delta.getContent() != null) {
                    System.out.print(delta.getContent());
                }
            }
        });
    }
}
