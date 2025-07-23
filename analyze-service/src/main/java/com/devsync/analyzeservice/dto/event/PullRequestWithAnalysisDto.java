package com.devsync.analyzeservice.dto.event;

import com.devsync.analyzeservice.dto.event.git.PullRequestDto;

import com.devsync.analyzeservice.dto.model.PullRequestAnalyzeDto;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class PullRequestWithAnalysisDto implements Serializable {
    private PullRequestDto pullRequest;
    private PullRequestAnalyze analyze;


    public PullRequestWithAnalysisDto(PullRequestDto pr, PullRequestAnalyze analyze) {
        this.analyze = analyze;
        this.pullRequest = pr;
    }


}
