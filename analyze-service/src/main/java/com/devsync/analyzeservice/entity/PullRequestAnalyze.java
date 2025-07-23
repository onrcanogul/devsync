package com.devsync.analyzeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "pull_request_analysis")
public class PullRequestAnalyze extends Analyze {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private long pullRequestId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pull_request_analysis_id")
    private List<CommitAnalyze> commitAnalyses = new ArrayList<>();
}
