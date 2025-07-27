package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.devsync.gitservice.entity.Outbox;
import com.devsync.gitservice.repository.OutboxRepository;
import com.devsync.gitservice.service.KafkaGitProducerService;
import com.devsync.gitservice.service.OutboxPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OutboxPublisherServiceImpl implements OutboxPublisherService {
    private final OutboxRepository outboxRepository;
    private final KafkaGitProducerService eventPublisher;
    private final ObjectMapper objectMapper;

    public OutboxPublisherServiceImpl(OutboxRepository outboxRepository, KafkaGitProducerService eventPublisher, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    public void publishOrderCreatedEvent() {
        log.info("Publishing order created event job was triggered");
        List<Outbox> outboxes = outboxRepository.findByPublishedFalse();
        outboxes.forEach(outbox -> {
            try {
                eventPublisher.sendPullRequest(objectMapper.readValue(outbox.getPayload(), GithubWebhookModel.class));
                log.info("Event has been published");
                outbox.setPublished(true);
                outboxRepository.save(outbox);
                log.info("Outbox has been published");
            }
            catch (Exception e) {
                log.error("Error while publishing outbox events: {}", e.getMessage());
            }
        });
    }

}
