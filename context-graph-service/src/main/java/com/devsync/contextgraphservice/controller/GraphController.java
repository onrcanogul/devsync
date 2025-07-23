package com.devsync.contextgraphservice.controller;

import com.devsync.contextgraphservice.entity.PullRequestNode;
import com.devsync.contextgraphservice.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/context-graph")
public class GraphController {
    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("{repoId}/{branch}")
    public ResponseEntity<List<PullRequestNode>> get(@PathVariable String branch, @PathVariable Long repoId) {
        return ResponseEntity.ok(graphService.get(repoId, branch));
    }

    @GetMapping("{repoId}")
    public ResponseEntity<List<PullRequestNode>> get(@PathVariable Long repoId) {
        return ResponseEntity.ok(graphService.get(repoId));
    }
}
