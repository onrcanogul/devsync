package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.dto.PullRequestDto;
import com.devsync.gitservice.service.KafkaGitProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaGitProducerServiceImpl implements KafkaGitProducerService {
    private final KafkaTemplate<String, PullRequestDto> kafkaTemplate;

    public KafkaGitProducerServiceImpl(KafkaTemplate<String, PullRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPullRequest(PullRequestDto model) {
        kafkaTemplate.send("pull-request-events", model);
    }
}
