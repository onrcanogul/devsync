package com.devsync.contextgraphservice.service;


import com.devsync.contextgraphservice.model.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.PullRequestNode;

import java.util.List;

public interface PullRequestNodeService {
    List<PullRequestNode> get(Long repoId, String branch);
    List<PullRequestNode> getByUser(String username);
    List<PullRequestNode> get(Long repoId);
    PullRequestNode getById(Long id);
    PullRequestNode saveFromPR(PullRequestWithAnalysisDto model);
}
