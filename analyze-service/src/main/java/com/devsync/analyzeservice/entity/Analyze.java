package com.devsync.analyzeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@ToString
public class Analyze {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long pullRequestId;
    private String repository;
    private String branch;
    private String author;
    private int commitCount;
    private int fileChangeCount;
    private int totalAdditions;
    private int totalDeletions;
    private int riskScore;
    @Column(columnDefinition = "text")
    private String generatedComment;
    private LocalDateTime analyzedAt;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    public int getFileChangeCount() {
        return fileChangeCount;
    }

    public void setFileChangeCount(int fileChangeCount) {
        this.fileChangeCount = fileChangeCount;
    }

    public String getGeneratedComment() {
        return generatedComment;
    }

    public void setGeneratedComment(String generatedComment) {
        this.generatedComment = generatedComment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(Long pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public int getTotalDeletions() {
        return totalDeletions;
    }

    public void setTotalDeletions(int totalDeletions) {
        this.totalDeletions = totalDeletions;
    }

    public int getTotalAdditions() {
        return totalAdditions;
    }

    public void setTotalAdditions(int totalAdditions) {
        this.totalAdditions = totalAdditions;
    }
}
