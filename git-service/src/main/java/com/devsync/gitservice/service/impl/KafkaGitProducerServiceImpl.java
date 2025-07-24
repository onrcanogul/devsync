package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.dto.model.GithubWebhookModel;
import com.devsync.gitservice.service.KafkaGitProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaGitProducerServiceImpl implements KafkaGitProducerService {
    private final KafkaTemplate<String, GithubWebhookModel> kafkaTemplate;

    public KafkaGitProducerServiceImpl(KafkaTemplate<String, GithubWebhookModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPullRequest(GithubWebhookModel model) {
        kafkaTemplate.send("pull-request-events", model);
    }
}
