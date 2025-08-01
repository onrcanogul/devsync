package com.devsync.analyzeservice.model.model.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PullRequestAIAnalyzeDto {
    private int riskScore;
    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;
}
