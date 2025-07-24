package com.devsync.gitservice.controller;

import com.devsync.gitservice.service.impl.GitServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/git")
public class GitWebhookController {

    private final GitServiceImpl gitService;

    public GitWebhookController(GitServiceImpl gitService) {
        this.gitService = gitService;
    }

    @PostMapping("/webhook/pull-request")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        String prId = String.valueOf(payload.get("number"));

        Map<String, Object> repository = (Map<String, Object>) payload.get("repository");
        String repo = (String) repository.get("name");

        Map<String, Object> ownerMap = (Map<String, Object>) repository.get("owner");
        String owner = (String) ownerMap.get("login");

        gitService.handlePullRequest(owner, repo, prId);
        return ResponseEntity.ok().build();
    }
}
