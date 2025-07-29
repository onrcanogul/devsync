package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.devsync.gitservice.service.KafkaGitProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaGitProducerServiceImpl implements KafkaGitProducerService {
    private final KafkaTemplate<String, GithubWebhookModel> githubWebhookModelKafkaTemplate;

    public KafkaGitProducerServiceImpl(KafkaTemplate<String, GithubWebhookModel> githubWebhookModelKafkaTemplate) {
        this.githubWebhookModelKafkaTemplate = githubWebhookModelKafkaTemplate;
    }

    @Override
    public void sendPullRequest(GithubWebhookModel model) {
        githubWebhookModelKafkaTemplate.send("pull-request-events", model);
    }

}
