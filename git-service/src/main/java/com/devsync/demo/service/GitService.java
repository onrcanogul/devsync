package com.devsync.demo.service;

import com.devsync.demo.client.GitApiClient;
import com.devsync.demo.dto.PullRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class GitService {

    private final GitApiClient gitApiClient;
    private final RabbitTemplate rabbitTemplate;

    public GitService(GitApiClient gitApiClient, RabbitTemplate rabbitTemplate) {
        this.gitApiClient = gitApiClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handlePullRequest(String owner, String repo, String prId) {
        PullRequestDto prDto = gitApiClient.fetchPullRequestWithCommitsAndDiffs(repo, prId);
        rabbitTemplate.convertAndSend("pull-request-events", "pr.created", prDto);
    }
}
