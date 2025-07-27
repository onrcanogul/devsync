package com.devsync.gitservice.controller;

import com.devsync.gitservice.dto.model.fromApi.RepositoryFromApi;
import com.devsync.gitservice.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/git")
public class GitController {
    private final GitService gitService;

    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryFromApi>> getRepositories(@PathVariable String username) {
        return ResponseEntity.ok(gitService.getRepositories(username));
    }
}
