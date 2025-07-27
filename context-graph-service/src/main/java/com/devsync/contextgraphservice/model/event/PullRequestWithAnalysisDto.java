package com.devsync.contextgraphservice.model.event;

import com.devsync.contextgraphservice.model.viewmodel.GithubWebhookModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PullRequestWithAnalysisDto {
    private GithubWebhookModel model;
    private PullRequestAnalyzeDto analyze;
}
