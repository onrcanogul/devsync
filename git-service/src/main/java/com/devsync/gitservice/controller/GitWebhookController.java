package com.devsync.gitservice.controller;

import com.devsync.gitservice.dto.model.GithubWebhookModel;
import com.devsync.gitservice.service.impl.GitServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/git")
public class GitWebhookController {

    private final ObjectMapper objectMapper;
    private final GitServiceImpl gitService;

    public GitWebhookController(ObjectMapper objectMapper, GitServiceImpl gitService) {
        this.objectMapper = objectMapper;
        this.gitService = gitService;
    }

    @PostMapping("/webhook/pull-request")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        GithubWebhookModel model = objectMapper.convertValue(payload, GithubWebhookModel.class);
        gitService.handlePullRequest(model);
        return ResponseEntity.ok().build();
    }
}
