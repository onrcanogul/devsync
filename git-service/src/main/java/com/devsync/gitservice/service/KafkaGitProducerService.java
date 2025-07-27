package com.devsync.gitservice.service;


import com.devsync.gitservice.dto.model.fromWebhook.GithubWebhookModel;

public interface KafkaGitProducerService {
    void sendPullRequest(GithubWebhookModel model);
}
