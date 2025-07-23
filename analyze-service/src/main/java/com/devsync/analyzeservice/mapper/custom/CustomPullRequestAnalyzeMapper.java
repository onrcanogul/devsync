package com.devsync.analyzeservice.mapper.custom;

import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import com.devsync.analyzeservice.dto.event.git.ChangedFileDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomPullRequestAnalyzeMapper {
    public PullRequestAnalyze mapFromDto(PullRequestDto dto) {
        PullRequestAnalyze analyze = new PullRequestAnalyze();
        analyze.setCommitCount(dto.getCommits().size());
        analyze.setFileChangeCount(dto.getDiff().getChangedFiles().size());
        analyze.setAnalyzedAt(LocalDateTime.now());
        analyze.setBranch(dto.getBranch());
        analyze.setPullRequestId(dto.getId());
        analyze.setAuthor(dto.getAuthor());
        analyze.setRepoId(dto.getBase().getRepo().getId());
        analyze.setRepoName(dto.getBase().getRepo().getName());
        analyze.setTotalAdditions(dto.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getAdditions).sum());
        analyze.setTotalDeletions(dto.getDiff().getChangedFiles().stream().mapToInt(ChangedFileDto::getDeletions).sum());
        return analyze;
    }
}
