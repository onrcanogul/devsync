package com.devsync.analyzeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
@ToString
public class Analyze {
    private String repoName;
    private Long repoId;
    private String branch;
    private String author;
    private int commitCount;
    private int fileChangeCount;
    private int totalAdditions;
    private int totalDeletions;
    private int riskScore;
    @Column(columnDefinition = "text")
    private String technicalComment;
    @Column(columnDefinition = "text")
    private String functionalComment;
    @Column(columnDefinition = "text")
    private String architecturalComment;
    private LocalDateTime analyzedAt;
}
