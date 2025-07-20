package com.devsync.analyzeservice.dto.event;

import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.entity.Analyze;


public class PullRequestWithAnalysisDto {
    private PullRequestDto pullRequest;
    private Analyze analyze;


    public Analyze getAnalyze() {
        return analyze;
    }

    public void setAnalyze(Analyze analyze) {
        this.analyze = analyze;
    }

    public PullRequestDto getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(PullRequestDto pullRequest) {
        this.pullRequest = pullRequest;
    }

    public PullRequestWithAnalysisDto(PullRequestDto pr, Analyze analyze) {
        this.analyze = analyze;
        this.pullRequest = pr;
    }


}
