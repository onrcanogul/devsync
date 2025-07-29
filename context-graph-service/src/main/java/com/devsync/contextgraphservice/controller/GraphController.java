package com.devsync.contextgraphservice.controller;

import com.devsync.contextgraphservice.entity.CommitNode;
import com.devsync.contextgraphservice.entity.PullRequestNode;
import com.devsync.contextgraphservice.entity.RepositoryNode;
import com.devsync.contextgraphservice.service.CommitNodeService;
import com.devsync.contextgraphservice.service.PullRequestNodeService;
import com.devsync.contextgraphservice.service.RepositoryNodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/context-graph")
public class GraphController {
    private final PullRequestNodeService pullRequestNodeService;
    private final CommitNodeService commitNodeService;
    private final RepositoryNodeService repositoryNodeService;

    public GraphController(PullRequestNodeService pullRequestNodeService, CommitNodeService commitNodeService, RepositoryNodeService repositoryNodeService) {
        this.pullRequestNodeService = pullRequestNodeService;
        this.commitNodeService = commitNodeService;
        this.repositoryNodeService = repositoryNodeService;
    }

    @GetMapping("repo/{username}")
    public ResponseEntity<List<RepositoryNode>> getRepositoriesByUser(@PathVariable String username) {
        return ResponseEntity.ok(repositoryNodeService.getByUser(username));
    }

    @GetMapping("{repoId}/{branch}")
    public ResponseEntity<List<PullRequestNode>> get(@PathVariable String branch, @PathVariable Long repoId) {
        return ResponseEntity.ok(pullRequestNodeService.get(repoId, branch));
    }

    @GetMapping("{repoId}")
    public ResponseEntity<List<PullRequestNode>> get(@PathVariable Long repoId) {
        return ResponseEntity.ok(pullRequestNodeService.get(repoId));
    }

    @GetMapping("commit")
    public ResponseEntity<List<CommitNode>> getCommitNode() {
        return ResponseEntity.ok(commitNodeService.get());
    }
}
