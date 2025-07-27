package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.client.GitApiClient;
import com.devsync.gitservice.model.fromApi.RepositoryFromApi;
import com.devsync.gitservice.model.fromWebhook.GithubWebhookModel;
import com.devsync.gitservice.entity.Outbox;
import com.devsync.gitservice.repository.OutboxRepository;
import com.devsync.gitservice.service.GitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GitServiceImpl implements GitService {
    private final GitApiClient gitApiClient;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    public GitServiceImpl(GitApiClient gitApiClient, ObjectMapper objectMapper, OutboxRepository outboxRepository) {
        this.gitApiClient = gitApiClient;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
    }

    @Override
    public void handlePullRequest(GithubWebhookModel model) throws JsonProcessingException {
        Outbox outbox = new Outbox();
        fillOutbox(outbox, model);
        outboxRepository.save(outbox);
    }

    @Override
    public List<RepositoryFromApi> getRepositories(String username) {
        return gitApiClient.getUsersRepositories(username);
    }

    private void fillOutbox(Outbox outbox, GithubWebhookModel model) throws JsonProcessingException {
        outbox.setPayload(objectMapper.writeValueAsString(model));
        outbox.setType(GithubWebhookModel.class.getTypeName());
        outbox.setAggregateType(GithubWebhookModel.class.getTypeName());
        outbox.setPublished(false);
        outbox.setAggregateId(String.valueOf(UUID.randomUUID()));
    }
}
