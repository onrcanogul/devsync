package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.model.model.ai.AnalyzeAIDto;
import com.devsync.analyzeservice.model.viewmodel.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PullRequestAnalyzerService {
    AnalyzeAIDto analyze(GithubWebhookModel model) throws JsonProcessingException;
}
