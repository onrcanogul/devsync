package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.service.GraphService;
import com.devsync.contextgraphservice.service.KafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private final GraphService graphService;

    public KafkaConsumerServiceImpl(GraphService graphService) {
        this.graphService = graphService;
    }

    @KafkaListener(topics = "pull-request.DLQ", groupId = "dlq-group")
    public void handleDlq(PullRequestWithAnalysisDto message) {
        log.error("DLQ triggered: {}", message.toString());
    }

    @KafkaListener(topics = "analyze-events", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(PullRequestWithAnalysisDto event) {
        log.info("Context Graph Consumer was triggered: {}", event.toString());
        graphService.saveFromPR(event);
    }
}
