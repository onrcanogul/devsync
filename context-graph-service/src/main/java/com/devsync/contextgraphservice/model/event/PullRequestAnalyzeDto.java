package com.devsync.contextgraphservice.model.event;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class PullRequestAnalyzeDto extends AnalyzeDto {
    private UUID id;
    private long pullRequestId;
    private List<CommitAnalyzeDto> commitAnalyses = new ArrayList<>();
}
