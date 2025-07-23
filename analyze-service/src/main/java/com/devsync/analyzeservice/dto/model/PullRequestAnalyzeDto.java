package com.devsync.analyzeservice.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class PullRequestAnalyzeDto extends AnalyzeDto {
    private UUID id;
    private long pullRequestId;
    private List<CommitAnalyzeDto> commitAnalyzes = new ArrayList<>();
}
