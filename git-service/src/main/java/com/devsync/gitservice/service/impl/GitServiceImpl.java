package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.client.GitApiClient;
import com.devsync.gitservice.dto.PullRequestDto;
import com.devsync.gitservice.dto.model.GithubWebhookModel;
import com.devsync.gitservice.entity.Outbox;
import com.devsync.gitservice.repository.OutboxRepository;
import com.devsync.gitservice.service.GitService;
import com.devsync.gitservice.service.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GitServiceImpl implements GitService {
    private final GitApiClient gitApiClient;
    private final JiraService jiraService;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    public GitServiceImpl(GitApiClient gitApiClient, JiraService jiraService, ObjectMapper objectMapper, OutboxRepository outboxRepository) {
        this.gitApiClient = gitApiClient;
        this.jiraService = jiraService;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
    }



    @Override
    public void handlePullRequest(GithubWebhookModel model) throws JsonProcessingException {
        Outbox outbox = new Outbox();
        fillOutbox(outbox, model);
        outboxRepository.save(outbox);
    }

    private void fillOutbox(Outbox outbox, GithubWebhookModel model) throws JsonProcessingException {
        outbox.setPayload(objectMapper.writeValueAsString(model));
        outbox.setType(PullRequestDto.class.getTypeName());
        outbox.setAggregateType(PullRequestDto.class.getTypeName());
        outbox.setPublished(false);
        outbox.setAggregateId(String.valueOf(UUID.randomUUID()));
    }
}
