package com.devsync.analyzeservice.service;


public interface OpenAIService {
    String send(String llm, String prompt);
}
