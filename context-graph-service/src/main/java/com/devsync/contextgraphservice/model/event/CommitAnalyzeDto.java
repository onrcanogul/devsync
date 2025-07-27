package com.devsync.contextgraphservice.model.event;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CommitAnalyzeDto extends AnalyzeDto {
    private UUID id;
    private String hash;
    private String message;
    private PullRequestAnalyzeDto pullRequestAnalyze;
}
