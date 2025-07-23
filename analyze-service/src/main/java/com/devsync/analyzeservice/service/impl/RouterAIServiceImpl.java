package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.configuration.OpenRouterConfigurations;
import com.devsync.analyzeservice.service.AIService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class RouterAIServiceImpl implements AIService {

    private final OpenRouterConfigurations properties;
    private final WebClient client;
    public RouterAIServiceImpl(WebClient.Builder webClientBuilder, OpenRouterConfigurations properties) {
        this.properties = properties;
        this.client = webClientBuilder
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getToken())
                .build();
    }

    @Override
    public String send(String llm, String prompt) {
        Map<String, Object> body = Map.of(
                "model", properties.getModel(),
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );
        return client.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getToken())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

