package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;

public interface KafkaProducerService {
    void sendPullRequest(PullRequestWithAnalysisDto model);
}
