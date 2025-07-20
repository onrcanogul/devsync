package com.devsync.contextgraphservice.service;

import com.devsync.contextgraphservice.dto.event.PullRequestDto;
import com.devsync.contextgraphservice.entity.PullRequestNode;

public interface GraphService {
    PullRequestNode saveFromPR(PullRequestDto model);
}
