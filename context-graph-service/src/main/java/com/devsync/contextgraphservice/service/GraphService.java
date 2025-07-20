package com.devsync.contextgraphservice.service;


import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.PullRequestNode;

public interface GraphService {
    PullRequestNode saveFromPR(PullRequestWithAnalysisDto model);
}
