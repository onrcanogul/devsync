package com.devsync.analyzeservice.model.model.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitAIAnalyzeDto {
    private String hash;
    private String message;
    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;
    private int riskScore;
}
