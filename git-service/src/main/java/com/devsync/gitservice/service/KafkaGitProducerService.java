package com.devsync.gitservice.service;


import com.devsync.gitservice.dto.model.GithubWebhookModel;

public interface KafkaGitProducerService {
    void sendPullRequest(GithubWebhookModel model);
}
