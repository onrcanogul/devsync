package com.devsync.analyzeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "commit-analysis")
public class CommitAnalyze extends Analyze {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String hash;
    private String message;
    @ManyToOne
    @JoinColumn(name = "pull_request_analyze_id")
    private PullRequestAnalyze pullRequestAnalyze;
}
