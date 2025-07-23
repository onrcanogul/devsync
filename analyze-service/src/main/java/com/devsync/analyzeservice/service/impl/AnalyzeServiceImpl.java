package com.devsync.analyzeservice.service.impl;

import com.devsync.analyzeservice.constant.Prompts;
import com.devsync.analyzeservice.dto.event.PullRequestWithAnalysisDto;
import com.devsync.analyzeservice.dto.event.git.ChangedFileDto;
import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.dto.model.ai.AnalyzeAIDto;
import com.devsync.analyzeservice.dto.model.AnalyzeDto;
import com.devsync.analyzeservice.dto.model.ai.CommitAIAnalyzeDto;
import com.devsync.analyzeservice.entity.Analyze;
import com.devsync.analyzeservice.entity.CommitAnalyze;
import com.devsync.analyzeservice.entity.Outbox;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import com.devsync.analyzeservice.mapper.AnalyzeMapper;
import com.devsync.analyzeservice.mapper.PullRequestAnalyzeMapper;
import com.devsync.analyzeservice.repository.OutboxRepository;
import com.devsync.analyzeservice.repository.PullRequestAnalyzeRepository;
import com.devsync.analyzeservice.service.AnalyzeService;
import com.devsync.analyzeservice.service.AIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    private final AIService AIService;
    private final PullRequestAnalyzeRepository repository;
    private final AnalyzeMapper analyzeMapper;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    public AnalyzeServiceImpl(AIService AIService, PullRequestAnalyzeRepository repository, AnalyzeMapper analyzeMapper, ObjectMapper objectMapper, OutboxRepository outboxRepository) {
        this.AIService = AIService;
        this.repository = repository;
        this.analyzeMapper = analyzeMapper;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
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
    public AnalyzeDto createAnalyze(PullRequestDto model) throws JsonProcessingException {
        PullRequestAnalyze analyze = new PullRequestAnalyze();
        fillAnalyze(analyze, model);
        getAnalyzeFromAI(analyze, model);
        PullRequestAnalyze createdAnalyze = repository.save(analyze);
        outboxRepository.save(fillOutbox(new PullRequestWithAnalysisDto(model, createdAnalyze)));
        return analyzeMapper.toDto(createdAnalyze);
    }
    
    private Outbox fillOutbox(PullRequestWithAnalysisDto model) throws JsonProcessingException {
        Outbox outbox = new Outbox();
        String analyzeObject = objectMapper.writeValueAsString(model);
        outbox.setPayload(analyzeObject);
        outbox.setPublished(false);
        outbox.setAggregateType(Analyze.class.getTypeName());
        outbox.setType(PullRequestWithAnalysisDto.class.getTypeName());
        outbox.setAggregateId(String.valueOf(model.getPullRequest().getId()));
        return outbox;
    }

    private void fillAnalyze(PullRequestAnalyze analyze, PullRequestDto model) {
        analyze.setCommitCount(model.getCommits().size());
        analyze.setFileChangeCount(model.getDiff().getChangedFiles().size());
        analyze.setAnalyzedAt(LocalDateTime.now());
        analyze.setBranch(model.getBranch());
        analyze.setPullRequestId(model.getId());
        analyze.setAuthor(model.getAuthor());
        analyze.setRepoId(model.getBase().getRepo().getId());
        analyze.setRepoName(model.getBase().getRepo().getName());
        analyze.setTotalAdditions(model.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getAdditions).sum());
        analyze.setTotalDeletions(model.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getDeletions).sum());
    }

    private void getAnalyzeFromAI(PullRequestAnalyze analyze, PullRequestDto model) throws JsonProcessingException {
        String prompt = Prompts.analyzePrompt(objectMapper.writeValueAsString(model));
        String answer = AIService.send("gpt-3.5-turbo-instruct", prompt);
        AnalyzeAIDto responseFromAI = deserialize(answer.trim());
        analyze.setTechnicalComment(responseFromAI.getPullRequestAnalysis().getTechnicalComment());
        analyze.setFunctionalComment(responseFromAI.getPullRequestAnalysis().getTechnicalComment());
        analyze.setArchitecturalComment(responseFromAI.getPullRequestAnalysis().getTechnicalComment());
        analyze.setCommitAnalyses(responseFromAI.getCommitAnalyses().stream().map(c -> {
            CommitAnalyze newAnalyze = new CommitAnalyze();
            newAnalyze.setHash(c.getHash());
            newAnalyze.setFunctionalComment(c.getFunctionalComment());
            newAnalyze.setArchitecturalComment(c.getArchitecturalComment());
            newAnalyze.setTechnicalComment(c.getTechnicalComment());
            return newAnalyze;
        }).toList());
    }


    private AnalyzeAIDto deserialize(String escapedJson) {
        String rawJson = escapedJson.trim();
        if (rawJson.contains("\"choices\"")) {
            rawJson = extractJsonContentOnly(rawJson); // sadece içeriği al
        }
        if (rawJson.contains("\\n") || rawJson.contains("\\\"")) {
            rawJson = StringEscapeUtils.unescapeJava(rawJson);
        }
        rawJson = rawJson.replaceAll("[\\n\\r]", "").trim();
        try {
            String cleaned = rawJson
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();
            return objectMapper.readValue(cleaned, AnalyzeAIDto.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    public String extractJsonContentOnly(String fullResponseJson) {
        try {
            JsonNode root = objectMapper.readTree(fullResponseJson);
            return root.path("choices").get(0).path("message").path("content").asText();

        } catch (IOException e) {
            throw new RuntimeException("Cannot extract content", e);
        }
    }
}
