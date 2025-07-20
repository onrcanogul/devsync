package com.devsync.contextgraphservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PullRequestWithAnalysisDto {
    private PullRequestDto pullRequest;
    private AnalyzeDto analyze;
}
