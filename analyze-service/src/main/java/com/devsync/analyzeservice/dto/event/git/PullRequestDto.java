package com.devsync.analyzeservice.dto.event.git;


import com.devsync.analyzeservice.dto.event.client.Base;
import com.devsync.analyzeservice.dto.event.client.Repo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PullRequestDto implements Serializable {
    private long id;
    private String title;
    private String author;
    private String branch;
    private List<CommitDto> commits;
    private List<IssueDto> issues = new ArrayList<>();
    private DiffDto diff;
    private Repo repo;
    private Base base;

    public PullRequestDto(long id, String title, String author, String branch, List<CommitDto> commits, DiffDto diff, Repo repo, Base base) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.branch = branch;
        this.commits = commits;
        this.diff = diff;
        this.repo = repo;
        this.base = base;
    }
    public PullRequestDto() {

    }

}