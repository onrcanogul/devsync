package com.devsync.analyzeservice.dto.model;

public class AnalyzeAIDto {
    private int riskScore;
    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getArchitecturalComment() {
        return architecturalComment;
    }

    public void setArchitecturalComment(String architecturalComment) {
        this.architecturalComment = architecturalComment;
    }

    public String getFunctionalComment() {
        return functionalComment;
    }

    public void setFunctionalComment(String functionalComment) {
        this.functionalComment = functionalComment;
    }

    public String getTechnicalComment() {
        return technicalComment;
    }

    public void setTechnicalComment(String technicalComment) {
        this.technicalComment = technicalComment;
    }
}
