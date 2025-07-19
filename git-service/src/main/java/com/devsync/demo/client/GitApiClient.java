package com.devsync.demo.client;

import com.devsync.demo.client.response.GitHubChangedFileResponse;
import com.devsync.demo.client.response.GitHubCommitResponse;
import com.devsync.demo.client.response.GitHubPullRequestResponse;
import com.devsync.demo.dto.ChangedFileDto;
import com.devsync.demo.dto.CommitDto;
import com.devsync.demo.dto.DiffDto;
import com.devsync.demo.dto.PullRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GitApiClient {

    private final WebClient webClient;

    public GitApiClient(WebClient.Builder builder, @Value("${github.token}") String githubToken) {
        this.webClient = builder
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }

    public PullRequestDto fetchPullRequestWithCommitsAndDiffs(String repo, String prNumber) {
        var prResponse = webClient.get()
                .uri("/repos/{repo}/pulls/{prNumber}", repo, prNumber)
                .retrieve()
                .bodyToMono(GitHubPullRequestResponse.class)
                .block();


        var commits = webClient.get()
                .uri("/repos/{repo}/pulls/{prNumber}/commits", repo, prNumber)
                .retrieve()
                .bodyToFlux(GitHubCommitResponse.class)
                .collectList()
                .block();

        var files = webClient.get()
                .uri("/repos/{repo}/pulls/{prNumber}/files", repo, prNumber)
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
                new DiffDto(fileDtos)
        );
    }
}
