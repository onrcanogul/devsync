package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.client.GitApiClient;
import com.devsync.gitservice.client.feign.AuthServiceClient;
import com.devsync.gitservice.constant.AppConstants;
import com.devsync.gitservice.factory.OutboxFactory;
import com.devsync.gitservice.model.fromApi.RepositoryFromApi;
import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.devsync.gitservice.entity.Outbox;
import com.devsync.gitservice.repository.OutboxRepository;
import com.devsync.gitservice.service.GitService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GitServiceImpl implements GitService {
    private final GitApiClient gitApiClient;
    private final OutboxRepository outboxRepository;
    private final AuthServiceClient authServiceClient;
    private final OutboxFactory outboxFactory;

    public GitServiceImpl(GitApiClient gitApiClient, OutboxRepository outboxRepository, AuthServiceClient authServiceClient, OutboxFactory outboxFactory) {
        this.gitApiClient = gitApiClient;
        this.outboxRepository = outboxRepository;
        this.authServiceClient = authServiceClient;
        this.outboxFactory = outboxFactory;
    }

    @Override
    public void handlePullRequest(GithubWebhookModel model) {
        Outbox outbox = outboxFactory.create(model, GithubWebhookModel.class, GithubWebhookModel.class, UUID.randomUUID().toString());
        outboxRepository.save(outbox);
    }

    @Override
    public List<RepositoryFromApi> getRepositories(String username) {
        String githubAccessToken = authServiceClient.getGitHubAccessToken(username);
        return gitApiClient.getUsersRepositories(username, githubAccessToken, AppConstants.webhookUrl);
    }

    @Override
    public void addWebhook(String accessToken, String owner, String repo) {
        HttpHeaders headers = new HttpHeaders();
        String githubAccessToken = authServiceClient.getGitHubAccessToken(owner);
        headers.setBearerAuth(githubAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> config = new HashMap<>();
        config.put("url", AppConstants.webhookUrl);
        config.put("content_type", "json");
        config.put("insecure_ssl", "0");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "web");
        requestBody.put("active", true);
        requestBody.put("events", List.of("push"));
        requestBody.put("config", config);
        gitApiClient.addWebhook(owner, repo, requestBody, headers);
    }
}
