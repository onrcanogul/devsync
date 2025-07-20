package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.dto.event.PullRequestDto;
import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.CommitNode;
import com.devsync.contextgraphservice.entity.IssueNode;
import com.devsync.contextgraphservice.entity.PullRequestNode;
import com.devsync.contextgraphservice.repository.PullRequestRepository;
import com.devsync.contextgraphservice.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GraphServiceImpl implements GraphService {
    private final PullRequestRepository pullRequestRepository;

    public GraphServiceImpl(PullRequestRepository pullRequestRepository) {
        this.pullRequestRepository = pullRequestRepository;
    }

    public PullRequestNode saveFromPR(PullRequestWithAnalysisDto model) {
        PullRequestNode pr = new PullRequestNode();
        pr.setId(model.getPullRequest().getId());
        pr.setTitle(model.getPullRequest().getTitle());
        pr.setBranch(model.getPullRequest().getBranch());
        pr.setCommits(model.getPullRequest().getCommits().stream().map(c -> new CommitNode(c.getSha(), c.getMessage())).toList());
        pr.setSolves(model.getPullRequest().getIssues().stream().map(i -> new IssueNode(generateRandomId() ,i.getSummary(), i.getKey())).toList());
        return pullRequestRepository.save(pr);
    }

    private long generateRandomId() {
        return ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L);
    }
}
