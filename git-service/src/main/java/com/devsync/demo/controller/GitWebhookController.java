package com.devsync.demo.controller;

import com.devsync.demo.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class GitWebhookController {

    private final GitService gitService;

    public GitWebhookController(GitService gitService) {
        this.gitService = gitService;
    }

    @PostMapping("/pull-request")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) {
        String prId = extractPrId(payload);
        gitService.handlePullRequest("devsync-org", "devsync-repo", prId);
        return ResponseEntity.ok().build();
    }

    private String extractPrId(Map<String, Object> payload) {
        return payload.get("pullRequestId").toString();
    }
}
