package com.devsync.analyzeservice.controller;

import com.devsync.analyzeservice.dto.model.AnalyzeDto;
import com.devsync.analyzeservice.service.AnalyzeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    public AnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<AnalyzeDto>> get(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(analyzeService.get(page, size));
    }

    @GetMapping("/repository/{repoName}")
    public ResponseEntity<List<AnalyzeDto>> get(@PathVariable String repoName) {
        return ResponseEntity.ok(analyzeService.getByRepository(repoName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyzeDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(analyzeService.getById(id));
    }

    @GetMapping("/pull-request/{prId}")
    public ResponseEntity<AnalyzeDto> get(@PathVariable Long prId) {
        return ResponseEntity.ok(analyzeService.getByPullRequest(prId));
    }


}
