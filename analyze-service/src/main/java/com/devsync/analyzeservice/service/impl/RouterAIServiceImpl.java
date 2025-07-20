package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.service.AIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class RouterAIServiceImpl implements AIService {

    @Value("${openrouter.token}")
    private String token;

    @Value("${openrouter.base-url}")
    private String baseUrl;

    @Value("${openrouter.model}")
    private String model;

    private final WebClient client;
    public RouterAIServiceImpl(WebClient.Builder webClientBuilder) {
         client = webClientBuilder
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    @Override
    public String send(String llm, String prompt) {
        Map<String, Object> body = Map.of(
                "model", model,
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );
        return client.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body) // request bir POJO ya da Map olabilir
                .retrieve()
                .bodyToMono(String.class) // veya doğru response DTO
                .block();
    }
}

