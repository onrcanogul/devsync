package com.devsync.analyzeservice.dto.event;


import java.io.Serializable;
import java.util.List;

public class PullRequestDto implements Serializable {
    public PullRequestDto(long id, String title, String author, String branch, List<CommitDto> commits, DiffDto diff) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.branch = branch;
        this.commits = commits;
        this.diff = diff;
    }

    public PullRequestDto() {

    }

    private long id;
    private String title;
    private String author;
    private String branch;
    private List<CommitDto> commits;
    private DiffDto diff;

    public static class Base {
        private Repo repo;
    }

    public static class Repo {
        private String name;
    }

    private Base base;

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getBranch() { return branch; }
    public List<CommitDto> getCommits() { return commits; }
    public DiffDto getDiff() { return diff; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setCommits(List<CommitDto> commits) { this.commits = commits; }
    public void setDiff(DiffDto diff) { this.diff = diff; }
}
