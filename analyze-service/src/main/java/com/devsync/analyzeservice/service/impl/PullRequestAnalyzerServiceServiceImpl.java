package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.constant.Prompts;
import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.dto.model.ai.AnalyzeAIDto;
import com.devsync.analyzeservice.service.AIService;
import com.devsync.analyzeservice.service.PullRequestAnalyzerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PullRequestAnalyzerServiceServiceImpl implements PullRequestAnalyzerService {

    private final ObjectMapper objectMapper;
    private final AIService aiService;

    public PullRequestAnalyzerServiceServiceImpl(ObjectMapper objectMapper, AIService aiService) {
        this.objectMapper = objectMapper;
        this.aiService = aiService;
    }

    public AnalyzeAIDto analyze(PullRequestDto model) throws JsonProcessingException {
        String prompt = Prompts.analyzePrompt(objectMapper.writeValueAsString(model));
        String answer = aiService.send("gpt-3.5-turbo-instruct", prompt);
        return deserialize(answer);
    }


    private AnalyzeAIDto deserialize(String escapedJson) {
        String rawJson = escapedJson.trim();
        if (rawJson.contains("\"choices\"")) {
            rawJson = extractJsonContentOnly(rawJson); // sadece içeriği al
        }
        if (rawJson.contains("\\n") || rawJson.contains("\\\"")) {
            rawJson = StringEscapeUtils.unescapeJava(rawJson);
        }
        rawJson = rawJson.replaceAll("[\\n\\r]", "").trim();
        try {
            String cleaned = rawJson
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();
            return objectMapper.readValue(cleaned, AnalyzeAIDto.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    private String extractJsonContentOnly(String fullResponseJson) {
        try {
            JsonNode root = objectMapper.readTree(fullResponseJson);
            return root.path("choices").get(0).path("message").path("content").asText();

        } catch (IOException e) {
            throw new RuntimeException("Cannot extract content", e);
        }
    }
}
