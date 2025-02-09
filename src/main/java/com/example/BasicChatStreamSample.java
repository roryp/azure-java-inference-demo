package com.example;

import java.util.ArrayList;
import java.util.List;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.StreamingChatCompletionsUpdate;
import com.azure.ai.inference.models.StreamingChatResponseMessageUpdate;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.IterableStream;

public final class BasicChatStreamSample {
    public static void main(String[] args) {
        // Retrieve the endpoint and API key from your environment variables.
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
        chatMessages.add(new ChatRequestUserMessage("Explain Riemann's conjecture in 1 paragraph."));

        // Create the completions options and specify your deployment/model name.
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");

        // Invoke the streaming method. Note that completeStream returns an IterableStream
        // of StreamingChatCompletionsUpdate.
        IterableStream<StreamingChatCompletionsUpdate> stream = client.completeStream(options);

        // Process each streaming update as it arrives.
        stream.stream().forEach(update -> {
            // Each update contains a streaming choice; check if it is not null.
            if (update.getChoice() == null) {
                return;
            }
            // Retrieve the delta update (partial content).
            StreamingChatResponseMessageUpdate delta = update.getChoice().getDelta();
            if (delta != null) {
                // Print the role if provided (typically appears once at the start).
                if (delta.getRole() != null) {
                    System.out.println("Role: " + delta.getRole());
                }
                // Print any incremental content.
                if (delta.getContent() != null) {
                    System.out.print(delta.getContent());
                }
            }
        });
    }
}
