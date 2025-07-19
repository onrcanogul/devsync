package com.devsync.gitservice.service;

import com.devsync.gitservice.dto.PullRequestDto;

public interface KafkaGitProducerService {
    void sendPullRequest(PullRequestDto model);
}
