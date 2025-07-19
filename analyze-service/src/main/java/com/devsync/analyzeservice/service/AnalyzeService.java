package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.dto.event.PullRequestDto;
import com.devsync.analyzeservice.dto.model.AnalyzeDto;

import java.util.List;
import java.util.UUID;

public interface AnalyzeService {
    List<AnalyzeDto> get(int page, int size);
    List<AnalyzeDto> getByRepository(String repoName);
    AnalyzeDto getById(UUID id);
    AnalyzeDto getByPullRequest(Long pullRequestId);
    AnalyzeDto createAnalyze(PullRequestDto model);
}
