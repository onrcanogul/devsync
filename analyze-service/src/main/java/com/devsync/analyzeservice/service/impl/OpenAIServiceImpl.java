package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.service.OpenAIService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenAiService openAiService;

    public OpenAIServiceImpl(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public String send(String llm, String prompt) {
        CompletionRequest request = CompletionRequest.builder()
                .model(llm)
                .prompt(prompt)
                .maxTokens(200)
                .temperature(0.7)
                .build();

        CompletionResult result = openAiService.createCompletion(request);
        return result.getChoices().get(0).getText().trim();
    }
}
