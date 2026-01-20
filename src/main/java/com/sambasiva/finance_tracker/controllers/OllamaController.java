package com.sambasiva.finance_tracker.controllers;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OllamaController {

    private final ChatClient client;

    public OllamaController(OllamaChatModel model) {
        this.client = ChatClient.create(model);
    }

    @PostMapping("/ai")
    public String askAi(@RequestParam(name = "prompt") String prompt, @RequestBody String expenses) {
        String str = """
                Here is a JSON object with external data: {expenses},
                "Please answer the following question based *only* on this data: {prompt}
                """;
        PromptTemplate temp = new PromptTemplate(str);
        return client.prompt(temp.create(Map.of("expenses", expenses, "prompt", prompt))).call().content();
    }
}
