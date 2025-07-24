package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.dto.model.ai.AnalyzeAIDto;
import com.devsync.analyzeservice.dto.model.AnalyzeDto;
import com.devsync.analyzeservice.dto.viewmodel.GithubWebhookModel;
import com.devsync.analyzeservice.entity.Analyze;
import com.devsync.analyzeservice.entity.CommitAnalyze;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import com.devsync.analyzeservice.factory.OutboxFactory;
import com.devsync.analyzeservice.mapper.AnalyzeMapper;
import com.devsync.analyzeservice.mapper.custom.CustomPullRequestAnalyzeMapper;
import com.devsync.analyzeservice.repository.OutboxRepository;
import com.devsync.analyzeservice.repository.PullRequestAnalyzeRepository;
import com.devsync.analyzeservice.service.AnalyzeService;
import com.devsync.analyzeservice.service.PullRequestAnalyzerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    private final PullRequestAnalyzeRepository repository;
    private final AnalyzeMapper analyzeMapper;
    private final PullRequestAnalyzerService pullRequestAnalyzerService;
    private final OutboxRepository outboxRepository;
    private final OutboxFactory outboxFactory;
    private final CustomPullRequestAnalyzeMapper customPullRequestAnalyzeMapper;

    public AnalyzeServiceImpl(PullRequestAnalyzeRepository repository, AnalyzeMapper analyzeMapper, PullRequestAnalyzerService pullRequestAnalyzerService, OutboxRepository outboxRepository, OutboxFactory outboxFactory, CustomPullRequestAnalyzeMapper customPullRequestAnalyzeMapper) {
        this.repository = repository;
        this.analyzeMapper = analyzeMapper;
        this.pullRequestAnalyzerService = pullRequestAnalyzerService;
        this.outboxRepository = outboxRepository;
        this.outboxFactory = outboxFactory;
        this.customPullRequestAnalyzeMapper = customPullRequestAnalyzeMapper;
    }

    public List<AnalyzeDto> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PullRequestAnalyze> analyzes = repository.findAll(pageable);
        return analyzes.stream().map(analyzeMapper::toDto).toList();
    }

    public List<AnalyzeDto> getByRepository(Long repoId) {
        List<PullRequestAnalyze> analyzes = repository.findByRepoId(repoId);
        return analyzes.stream().map(analyzeMapper::toDto).toList();
    }

    public AnalyzeDto getById(UUID id) {
        Analyze analyze = repository.findById(id).orElseThrow(NullPointerException::new);
        return analyzeMapper.toDto(analyze);
    }

    public AnalyzeDto getByPullRequest(Long pullRequestId) {
        Analyze analyze = repository.findByPullRequestId(pullRequestId).orElseThrow(NullPointerException::new);
        return analyzeMapper.toDto(analyze);
    }


    @Transactional
    public AnalyzeDto createAnalyze(GithubWebhookModel model) throws JsonProcessingException {
        PullRequestAnalyze analyze = customPullRequestAnalyzeMapper.mapFromDto(model);
        getAnalyzeFromAI(analyze, model);
        PullRequestAnalyze createdAnalyze = repository.save(analyze);
        outboxRepository.save(outboxFactory.create(new PullRequestWithAnalysisDto(model, createdAnalyze), PullRequestAnalyze.class, PullRequestWithAnalysisDto.class, createdAnalyze.getId().toString()));
        return analyzeMapper.toDto(createdAnalyze);
    }

    private void getAnalyzeFromAI(PullRequestAnalyze analyze, GithubWebhookModel model) throws JsonProcessingException {
        AnalyzeAIDto analyzedPullRequest = pullRequestAnalyzerService.analyze(model);
        analyze.setTechnicalComment(analyzedPullRequest.getPullRequestAnalysis().getTechnicalComment());
        analyze.setFunctionalComment(analyzedPullRequest.getPullRequestAnalysis().getTechnicalComment());
        analyze.setArchitecturalComment(analyzedPullRequest.getPullRequestAnalysis().getTechnicalComment());
        analyze.setCommitAnalyses(analyzedPullRequest.getCommitAnalyses().stream().map(c -> {
            CommitAnalyze newAnalyze = new CommitAnalyze();
            newAnalyze.setHash(c.getHash());
            newAnalyze.setFunctionalComment(c.getFunctionalComment());
            newAnalyze.setArchitecturalComment(c.getArchitecturalComment());
            newAnalyze.setTechnicalComment(c.getTechnicalComment());
            return newAnalyze;
        }).toList());
    }



}
