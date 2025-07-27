package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.model.event.PullRequestWithAnalysisDto;

public interface KafkaProducerService {
    void sendPullRequest(PullRequestWithAnalysisDto model);
}
