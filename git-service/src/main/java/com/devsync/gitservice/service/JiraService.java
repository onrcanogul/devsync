package com.devsync.gitservice.service;

import com.devsync.gitservice.dto.PullRequestDto;

public interface JiraService {
    void enrichPullRequestWithJiraIssues(PullRequestDto prDto);
}
