package com.example;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeepseekChatService {

    @Value("${AZURE_OPENAI_ENDPOINT:}")
    private String endpoint;

    @Value("${AZURE_OPENAI_API_KEY:}")
    private String apiKey;

    public boolean areCredentialsSet() {
        return endpoint != null && !endpoint.isEmpty() && apiKey != null && !apiKey.isEmpty();
    }

    public String getChatResponse(String prompt, String systemPrompt) {
        if (!areCredentialsSet()) {
            return "Error: Azure OpenAI credentials are not set.";
        }

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        
        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            chatMessages.add(new ChatRequestSystemMessage(systemPrompt));
        } else {
            chatMessages.add(new ChatRequestSystemMessage("You are a helpful AI assistant."));
        }
        
        chatMessages.add(new ChatRequestUserMessage(prompt));

        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
        options.setModel("DeepSeek-R1");

        try {
            ChatCompletions response = client.complete(options);
            if (response != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response received from the model.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}