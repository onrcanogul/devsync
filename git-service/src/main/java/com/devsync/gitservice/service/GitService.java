package com.devsync.gitservice.service;

import com.devsync.gitservice.dto.model.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface GitService {
    void handlePullRequest(GithubWebhookModel model) throws JsonProcessingException;
}
