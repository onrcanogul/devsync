package com.devsync.contextgraphservice.service;

import com.devsync.contextgraphservice.model.event.PullRequestWithAnalysisDto;

public interface KafkaConsumerService {
    void handleDlq(PullRequestWithAnalysisDto message);
    void listen(PullRequestWithAnalysisDto event);
}
