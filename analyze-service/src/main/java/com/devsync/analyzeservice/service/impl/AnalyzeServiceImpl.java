package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.constant.Prompts;
import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.dto.event.git.ChangedFileDto;
import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.dto.model.AnalyzeAIDto;
import com.devsync.analyzeservice.dto.model.AnalyzeDto;
import com.devsync.analyzeservice.entity.Analyze;
import com.devsync.analyzeservice.entity.Outbox;
import com.devsync.analyzeservice.mapper.AnalyzeMapper;
import com.devsync.analyzeservice.repository.AnalyzeRepository;
import com.devsync.analyzeservice.repository.OutboxRepository;
import com.devsync.analyzeservice.service.AnalyzeService;
import com.devsync.analyzeservice.service.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    private final OpenAIService openAIService;
    private final AnalyzeRepository repository;
    private final AnalyzeMapper analyzeMapper;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    public AnalyzeServiceImpl(OpenAIService openAIService, AnalyzeRepository repository, AnalyzeMapper analyzeMapper, ObjectMapper objectMapper, OutboxRepository outboxRepository) {
        this.openAIService = openAIService;
        this.repository = repository;
        this.analyzeMapper = analyzeMapper;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
    }

    public List<AnalyzeDto> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Analyze> analyzes = repository.findAll(pageable);
        return analyzes.stream().map(analyzeMapper::toDto).toList();
    }

    public List<AnalyzeDto> getByRepository(String repoName) {
        List<Analyze> analyzes = repository.findByRepository(repoName);
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
    public AnalyzeDto createAnalyze(PullRequestDto model) throws JsonProcessingException {
        Analyze analyze = new Analyze();
        UUID analyzeId = UUID.randomUUID();
        analyze.setId(analyzeId);
        fillAnalyze(analyze, model);
        getAnalyzeFromAI(analyze, model);
        Analyze createdAnalyze = repository.save(analyze);
        outboxRepository.save(fillOutbox(new PullRequestWithAnalysisDto(model, analyze)));
        return analyzeMapper.toDto(createdAnalyze);
    }
    
    private Outbox fillOutbox(PullRequestWithAnalysisDto model) throws JsonProcessingException {
        Outbox outbox = new Outbox();
        String analyzeObject = objectMapper.writeValueAsString(model);
        outbox.setPayload(analyzeObject);
        outbox.setPublished(false);
        outbox.setAggregateType(Analyze.class.getTypeName());
        outbox.setType(PullRequestWithAnalysisDto.class.getTypeName());
        outbox.setAggregateId(model.getAnalyze().getId().toString());
        return outbox;
    }

    private void fillAnalyze(Analyze analyze, PullRequestDto model) {
        analyze.setCommitCount(model.getCommits().size());
        analyze.setFileChangeCount(model.getDiff().getChangedFiles().size());
        analyze.setAnalyzedAt(LocalDateTime.now());
        analyze.setBranch(model.getBranch());
        analyze.setPullRequestId(model.getId());
        analyze.setAuthor(model.getAuthor());
        analyze.setTotalAdditions(model.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getAdditions).sum());
        analyze.setTotalDeletions(model.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getDeletions).sum());
    }

    private void getAnalyzeFromAI(Analyze analyze, PullRequestDto model) throws JsonProcessingException {
        String prompt = Prompts.analyzePrompt(objectMapper.writeValueAsString(model));
        String answer = openAIService.send("gpt-3.5-turbo-instruct", prompt);
        AnalyzeAIDto responseFromAI = objectMapper.convertValue(answer, AnalyzeAIDto.class);
        analyze.setTechnicalComment(responseFromAI.getTechnicalComment());
        analyze.setFunctionalComment(responseFromAI.getFunctionalComment());
        analyze.setArchitecturalComment(responseFromAI.getArchitecturalComment());
    }
}
