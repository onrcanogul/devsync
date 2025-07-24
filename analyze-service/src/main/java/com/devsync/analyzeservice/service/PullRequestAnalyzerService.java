package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.dto.model.ai.AnalyzeAIDto;
import com.devsync.analyzeservice.dto.viewmodel.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PullRequestAnalyzerService {
    AnalyzeAIDto analyze(GithubWebhookModel model) throws JsonProcessingException;
}
