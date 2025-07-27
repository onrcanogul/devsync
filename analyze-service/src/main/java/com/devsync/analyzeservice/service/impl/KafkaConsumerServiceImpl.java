package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.model.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.model.viewmodel.GithubWebhookModel;
import com.devsync.analyzeservice.service.AnalyzeService;
import com.devsync.analyzeservice.service.KafkaConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final AnalyzeService analyzeService;

    public KafkaConsumerServiceImpl(AnalyzeServiceImpl analyzeService) {
        this.analyzeService = analyzeService;
    }

    @KafkaListener(topics = "pull-request.DLQ", groupId = "dlq-group")
    public void handleDlq(PullRequestWithAnalysisDto message) {
        log.info("DLQ triggered: {}", message.toString());
    }
    @KafkaListener(topics = "pull-request-events", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(GithubWebhookModel model) throws JsonProcessingException {
        log.info("Analyze consumer have been triggered:  {}", model.toString());
        analyzeService.createAnalyze(model);
    }
}
