package com.devsync.analyzeservice.dto.event;

import com.devsync.analyzeservice.dto.viewmodel.GithubWebhookModel;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class PullRequestWithAnalysisDto implements Serializable {
    private GithubWebhookModel model;
    private PullRequestAnalyze analyze;

    public PullRequestWithAnalysisDto(GithubWebhookModel model, PullRequestAnalyze analyze) {
        this.analyze = analyze;
        this.model = model;
    }


}
