package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.dto.model.ai.AnalyzeAIDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PullRequestAnalyzerService {
    AnalyzeAIDto analyze(PullRequestDto model) throws JsonProcessingException;
}
