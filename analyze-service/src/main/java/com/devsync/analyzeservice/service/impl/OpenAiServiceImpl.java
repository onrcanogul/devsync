package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.configuration.OpenAIConfigurations;
import com.devsync.analyzeservice.service.AIService;


import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAiServiceImpl implements AIService {

    private final OpenAiService service ;

    public OpenAiServiceImpl(OpenAIConfigurations openAIConfigurations) {
        this.service = new OpenAiService(openAIConfigurations.getToken());
    }

    @Override
    public String send(String llm, String prompt) {
        ChatMessage userMessage = new ChatMessage("user", prompt);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(llm)
                .messages(List.of(userMessage))
                .temperature(0.7)
                .maxTokens(512)
                .build();

        List<ChatCompletionChoice> choices = service.createChatCompletion(request).getChoices();
        if (!choices.isEmpty()) {
            return choices.get(0).getMessage().getContent();
        }

        return "[No response from OpenAI]";
    }
}
