package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.service.AnalyzeService;
import com.devsync.analyzeservice.service.KafkaConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final AnalyzeService analyzeService;

    public KafkaConsumerServiceImpl(AnalyzeServiceImpl analyzeService) {
        this.analyzeService = analyzeService;
    }

    @KafkaListener(topics = "pull-request.DLQ", groupId = "dlq-group")
    public void handleDlq(PullRequestWithAnalysisDto message) {
        System.out.println("DLQ triggered: " + message.toString());
    }
    @KafkaListener(topics = "pull-request-events", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(PullRequestDto model) throws JsonProcessingException {
        System.out.println("PR Model: " + model.toString());
        analyzeService.createAnalyze(model);
    }
}
