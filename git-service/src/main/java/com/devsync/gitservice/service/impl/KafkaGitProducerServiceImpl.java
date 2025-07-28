package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.model.event.CreateRepositoryModel;
import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.devsync.gitservice.service.KafkaGitProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaGitProducerServiceImpl implements KafkaGitProducerService {
    private final KafkaTemplate<String, GithubWebhookModel> githubWebhookModelKafkaTemplate;
    private final KafkaTemplate<String, CreateRepositoryModel> createRepositoryModelKafkaTemplate;

    public KafkaGitProducerServiceImpl(KafkaTemplate<String, GithubWebhookModel> githubWebhookModelKafkaTemplate, KafkaTemplate<String, CreateRepositoryModel> createRepositoryModelKafkaTemplate) {
        this.githubWebhookModelKafkaTemplate = githubWebhookModelKafkaTemplate;
        this.createRepositoryModelKafkaTemplate = createRepositoryModelKafkaTemplate;
    }

    @Override
    public void sendPullRequest(GithubWebhookModel model) {
        githubWebhookModelKafkaTemplate.send("pull-request-events", model);
    }

    @Override
    public void sendCreateRepository(CreateRepositoryModel model) {
        createRepositoryModelKafkaTemplate.send("create-repository-events", model);
    }
}
