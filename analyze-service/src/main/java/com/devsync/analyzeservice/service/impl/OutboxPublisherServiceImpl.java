package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.entity.Outbox;
import com.devsync.analyzeservice.repository.OutboxRepository;
import com.devsync.analyzeservice.service.KafkaProducerService;
import com.devsync.analyzeservice.service.OutboxPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboxPublisherServiceImpl implements OutboxPublisherService {
    private final KafkaProducerService eventPublisher;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OutboxPublisherServiceImpl(KafkaProducerServiceImpl eventPublisher, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    public void publishEvent() {
        List<Outbox> outboxes = outboxRepository.findByPublishedFalse();
        outboxes.forEach(outbox -> {
            try {
                eventPublisher.sendPullRequest(objectMapper.readValue(outbox.getPayload(), PullRequestWithAnalysisDto.class));
                outbox.setPublished(true);
                outboxRepository.save(outbox);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        });
    }
}
