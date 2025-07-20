package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.dto.event.PullRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final AnalyzeServiceImpl analyzeService;

    public KafkaConsumerService(AnalyzeServiceImpl analyzeService) {
        this.analyzeService = analyzeService;
    }

    @KafkaListener(topics = "pull-request-events", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(PullRequestDto model) throws JsonProcessingException {
        System.out.println("PR Model: " + model.toString());
        analyzeService.createAnalyze(model);
    }
}
