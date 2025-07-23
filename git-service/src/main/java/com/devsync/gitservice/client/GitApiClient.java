package com.devsync.gitservice.client;

import com.devsync.gitservice.client.response.github.GitHubChangedFileResponse;
import com.devsync.gitservice.client.response.github.GitHubCommitResponse;
import com.devsync.gitservice.client.response.github.GitHubPullRequestResponse;
import com.devsync.gitservice.configuration.GitHubProperties;
import com.devsync.gitservice.dto.ChangedFileDto;
import com.devsync.gitservice.dto.CommitDto;
import com.devsync.gitservice.dto.DiffDto;
import com.devsync.gitservice.dto.PullRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GitApiClient {

    private final WebClient webClient;

    public GitApiClient(WebClient.Builder builder, GitHubProperties properties) {
        this.webClient = builder
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + properties.getToken())
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }

    public PullRequestDto fetchPullRequestWithCommitsAndDiffs(String owner, String repo, String prNumber) {
        var prResponse = webClient.get()
                .uri("/repos/{owner}/{repo}/pulls/{prNumber}?state=all", owner, repo, prNumber)
                .retrieve()
                .bodyToMono(GitHubPullRequestResponse.class)
                .block();

        var commits = webClient.get()
                .uri("/repos/{owner}/{repo}/pulls/{prNumber}/commits", owner, repo, prNumber)
                .retrieve()
                .bodyToFlux(GitHubCommitResponse.class)
                .collectList()
                .block();

        var files = webClient.get()
                .uri("/repos/{owner}/{repo}/pulls/{prNumber}/files", owner, repo, prNumber)
                .retrieve()
                .bodyToFlux(GitHubChangedFileResponse.class)
                .collectList()
                .block();

        List<CommitDto> commitDtos = commits.stream()
                .map(c -> new CommitDto(c.getSha(), c.getCommit().getMessage(), c.getCommit().getCommitter().getDate()))
                .collect(Collectors.toList());

        List<ChangedFileDto> fileDtos = files.stream()
                .map(f -> new ChangedFileDto(f.getFilename(), f.getStatus(), f.getAdditions(), f.getDeletions()))
                .collect(Collectors.toList());

        return new PullRequestDto(
                prResponse.getId(),
                prResponse.getTitle(),
                prResponse.getUser().getLogin(),
                prResponse.getHead().getRef(),
                commitDtos,
                new DiffDto(fileDtos),
                prResponse.getRepo(),
                prResponse.getBase()
        );
    }
}
