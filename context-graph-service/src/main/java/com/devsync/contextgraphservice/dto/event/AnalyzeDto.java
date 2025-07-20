package com.devsync.contextgraphservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnalyzeDto {
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
    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;

    private LocalDateTime analyzedAt;
}
