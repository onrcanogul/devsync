package com.devsync.gitservice.service;

import com.devsync.gitservice.model.fromApi.RepositoryFromApi;
import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GitService {
    void handlePullRequest(GithubWebhookModel model) throws JsonProcessingException;
    List<RepositoryFromApi> getRepositories(String username);
    void addWebhook(String accessToken, String owner, String repo);
}
