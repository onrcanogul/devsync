package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.dto.event.CommitAnalyzeDto;
import com.devsync.contextgraphservice.dto.event.PullRequestAnalyzeDto;
import com.devsync.contextgraphservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.contextgraphservice.entity.*;
import com.devsync.contextgraphservice.repository.PullRequestRepository;
import com.devsync.contextgraphservice.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private void fillPrNode(PullRequestNode pr, PullRequestWithAnalysisDto dto) {
        pr.setId(System.currentTimeMillis());
        pr.setRepoId(dto.getModel().getRepository().getId());
        pr.setRepoName(dto.getModel().getRepository().getName());
        String[] branchParts = dto.getModel().getRef().split("/");
        String branch = branchParts[branchParts.length - 1];
        pr.setBranch(branch);
        pr.setPusher(dto.getModel().getPusher().getName());
        if (dto.getModel().getHead_commit() != null) {
            pr.setHeadCommitMessage(dto.getModel().getHead_commit().getMessage());
            pr.setHeadCommitSha(dto.getModel().getHead_commit().getId());
        }
        pr.setCommitCount(dto.getModel().getCommits() != null ? dto.getModel().getCommits().size() : 0);
        if (dto.getModel().getCommits() != null) {
            pr.setCommits(
                    dto.getModel().getCommits().stream()
                            .map(c -> new CommitNode(c.getId(), c.getMessage()))
                            .toList()
            );
        }
        if (dto.getModel().getSender() != null) {
            UserNode user = new UserNode();
            user.setGithubId(dto.getModel().getSender().getId());
            user.setUsername(dto.getModel().getSender().getLogin());
            user.setAvatarUrl(dto.getModel().getSender().getAvatar_url());
            user.setUserType(dto.getModel().getSender().getType());
            pr.setCreatedBy(user);
        }
        pr.setSolves(new ArrayList<>());
        setNodesAnalysis(pr, dto);
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
}

