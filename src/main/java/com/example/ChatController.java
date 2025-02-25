package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    private final DeepseekChatService chatService;

    @Autowired
    public ChatController(DeepseekChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/api/chat")
    @ResponseBody
    public String getChatResponse(@RequestParam String prompt, 
                                @RequestParam(required = false) String systemPrompt) {
        return chatService.getChatResponse(prompt, systemPrompt);
    }
}