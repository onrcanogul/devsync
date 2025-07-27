package com.devsync.gitservice.service;

import com.devsync.gitservice.dto.model.fromApi.RepositoryFromApi;
import com.devsync.gitservice.dto.model.fromWebhook.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GitService {
    void handlePullRequest(GithubWebhookModel model) throws JsonProcessingException;
    List<RepositoryFromApi> getRepositories(String username);
}
