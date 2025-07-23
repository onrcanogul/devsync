package com.devsync.gitservice.client.response.github;

import com.devsync.gitservice.dto.PullRequestDto;
import com.devsync.gitservice.dto.client.Base;
import com.devsync.gitservice.dto.client.Head;
import com.devsync.gitservice.dto.client.Repo;
import com.devsync.gitservice.dto.client.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GitHubPullRequestResponse {
    private long id;
    private String title;
    private User user;
    private Head head;
    private Base base;
    private Repo repo;
}
