package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.client.GitApiClient;
import com.devsync.gitservice.dto.PullRequestDto;
import com.devsync.gitservice.entity.Outbox;
import com.devsync.gitservice.repository.OutboxRepository;
import com.devsync.gitservice.service.GitService;
import com.devsync.gitservice.service.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

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

    public void handlePullRequest(String owner, String repo, String prId) throws JsonProcessingException {
        PullRequestDto dto = gitApiClient.fetchPullRequestWithCommitsAndDiffs(owner, repo, prId);
        // jiraService.enrichPullRequestWithJiraIssues(dto);
        Outbox outbox = new Outbox();
        fillOutbox(outbox, dto);
        outboxRepository.save(outbox);
    }

    private void fillOutbox(Outbox outbox, PullRequestDto dto) throws JsonProcessingException {
        outbox.setPayload(objectMapper.writeValueAsString(dto));
        outbox.setType(PullRequestDto.class.getTypeName());
        outbox.setAggregateType(PullRequestDto.class.getTypeName());
        outbox.setPublished(false);
        outbox.setAggregateId(String.valueOf(dto.getId()));
    }
}
