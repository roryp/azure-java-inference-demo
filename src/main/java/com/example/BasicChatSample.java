package com.example;

import java.util.ArrayList;
import java.util.List;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatChoice;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.ChatResponseMessage;
import com.azure.core.credential.AzureKeyCredential;

public final class BasicChatSample {
    /**
     * @param args Unused. Arguments to the program.
     */
    public static void main(String[] args) {

        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");  
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");  
        
        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant"));
        chatMessages.add(new ChatRequestUserMessage("Explain Riemann's conjecture in 1 paragraph"));

        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");  // Explicitly set the model (deployment) name

        ChatCompletions chatCompletions = client.complete(options);

        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatResponseMessage message = choice.getMessage();
            System.out.println("Response:" + message.getContent());
        }
    }
}