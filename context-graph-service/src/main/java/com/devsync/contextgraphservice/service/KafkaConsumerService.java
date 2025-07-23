package com.devsync.contextgraphservice.service;

import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;

public interface KafkaConsumerService {
    void handleDlq(PullRequestWithAnalysisDto message);
    void listen(PullRequestWithAnalysisDto event);
}
