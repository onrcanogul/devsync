package com.devsync.gitservice.client;

import com.devsync.gitservice.configuration.GitHubProperties;
import com.devsync.gitservice.dto.model.fromApi.RepositoryFromApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

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

    public List<RepositoryFromApi> getUsersRepositories(String username) {
        return webClient.get()
                .uri("https://api.github.com/users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(RepositoryFromApi.class)
                .collectList()
                .block();
    }
}
