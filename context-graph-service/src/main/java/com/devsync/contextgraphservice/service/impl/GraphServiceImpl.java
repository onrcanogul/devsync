package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.dto.event.CommitAnalyzeDto;
import com.devsync.contextgraphservice.dto.event.PullRequestAnalyzeDto;
import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.*;
import com.devsync.contextgraphservice.repository.PullRequestRepository;
import com.devsync.contextgraphservice.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GraphServiceImpl implements GraphService {
    private final PullRequestRepository pullRequestRepository;

    public GraphServiceImpl(PullRequestRepository pullRequestRepository) {
        this.pullRequestRepository = pullRequestRepository;
    }

    public List<PullRequestNode> get(Long repoId, String branch) {
        return pullRequestRepository.findByRepoIdAndBranch(repoId, branch);
    }

    public List<PullRequestNode> get(Long repoId) {
        return pullRequestRepository.findByRepoId(repoId);
    }

    public PullRequestNode saveFromPR(PullRequestWithAnalysisDto model) {
        PullRequestNode pr = new PullRequestNode();
        fillPrNode(pr, model);
        return pullRequestRepository.save(pr);
    }

    private void fillPrNode(PullRequestNode pr, PullRequestWithAnalysisDto model) {
        pr.setId(model.getPullRequest().getId());
        pr.setRepoId(model.getPullRequest().getBase().getRepo().getId());
        pr.setRepoName(model.getPullRequest().getBase().getRepo().getName());
        pr.setTitle(model.getPullRequest().getTitle());
        pr.setBranch(model.getPullRequest().getBranch());
        pr.setCommits(model.getPullRequest().getCommits().stream().map(c -> new CommitNode(c.getSha(), c.getMessage())).toList());
        pr.setSolves(model.getPullRequest().getIssues().stream().map(i -> new IssueNode(generateRandomId() ,i.getSummary(), i.getKey())).toList());
        setNodesAnalysis(pr, model);
    }

    private void setNodesAnalysis(PullRequestNode pr, PullRequestWithAnalysisDto model) {
        PullRequestAnalyzeDto analyzeDto = model.getAnalyze();
        if (analyzeDto == null) return;

        PullRequestAnalysisNode pullRequestAnalysisNode = new PullRequestAnalysisNode();
        pullRequestAnalysisNode.setFunctionalComment(analyzeDto.getFunctionalComment());
        pullRequestAnalysisNode.setRiskScore(analyzeDto.getRiskScore());
        pullRequestAnalysisNode.setArchitecturalComment(analyzeDto.getArchitecturalComment());
        pullRequestAnalysisNode.setTechnicalComment(analyzeDto.getTechnicalComment());
        pr.setAnalysis(pullRequestAnalysisNode);

        Map<String, CommitAnalyzeDto> commitAnalysisMap = analyzeDto.getCommitAnalyses()
                .stream()
                .collect(Collectors.toMap(CommitAnalyzeDto::getHash, Function.identity()));

        pr.getCommits().forEach(commitNode -> {
            CommitAnalyzeDto analysis = commitAnalysisMap.get(commitNode.getHash());
            if (analysis != null) {
                CommitAnalysisNode commitAnalysisNode = new CommitAnalysisNode();
                commitAnalysisNode.setHash(analysis.getHash());
                commitAnalysisNode.setCommitRiskScore(analysis.getRiskScore());
                commitAnalysisNode.setTechnicalComment(analysis.getTechnicalComment());
                commitAnalysisNode.setArchitecturalComment(analysis.getArchitecturalComment());
                commitAnalysisNode.setFunctionalComment(analysis.getFunctionalComment());
                commitNode.setAnalysis(commitAnalysisNode);
            }
        });
    }

    private long generateRandomId() {
        return ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L);
    }
}

