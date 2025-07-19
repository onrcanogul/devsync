package com.devsync.gitservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GitService {
    void handlePullRequest(String owner, String repo, String prId) throws JsonProcessingException;
}
