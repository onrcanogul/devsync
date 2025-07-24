package com.devsync.analyzeservice.mapper.custom;

import com.devsync.analyzeservice.dto.viewmodel.GithubWebhookModel;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomPullRequestAnalyzeMapper {

    public PullRequestAnalyze mapFromDto(GithubWebhookModel dto) {
        PullRequestAnalyze analyze = new PullRequestAnalyze();

        analyze.setCommitCount(dto.getCommits().size());
        analyze.setFileChangeCount(dto.getHead_commit().getModified().size());
        analyze.setAnalyzedAt(LocalDateTime.now());

        String[] refParts = dto.getRef().split("/");
        String branchName = refParts[refParts.length - 1];
        analyze.setBranch(branchName);

        analyze.setPullRequestId(0);

        analyze.setAuthor(dto.getHead_commit().getAuthor().getName());

        analyze.setRepoId(dto.getRepository().getId());
        analyze.setRepoName(dto.getRepository().getName());

        int additions = dto.getCommits().stream()
                .flatMap(commit -> commit.getModified().stream())
                .mapToInt(file -> 1)
                .sum();
        analyze.setTotalAdditions(additions);

        int deletions = dto.getCommits().stream()
                .flatMap(commit -> commit.getRemoved().stream())
                .mapToInt(file -> 1)
                .sum();
        analyze.setTotalDeletions(deletions);
        return analyze;
    }

}
