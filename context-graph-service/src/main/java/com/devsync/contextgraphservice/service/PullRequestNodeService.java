package com.devsync.contextgraphservice.service;


import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.PullRequestNode;

import java.util.List;

public interface PullRequestNodeService {
    List<PullRequestNode> get(Long repoId, String branch);
    List<PullRequestNode> get(Long repoId);
    PullRequestNode saveFromPR(PullRequestWithAnalysisDto model);
}
