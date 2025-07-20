package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.service.KafkaProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, PullRequestWithAnalysisDto> kafkaTemplate;

    public KafkaProducerServiceImpl(KafkaTemplate<String, PullRequestWithAnalysisDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPullRequest(PullRequestWithAnalysisDto model) {
        kafkaTemplate.send("analyze-events", model);
    }
}
