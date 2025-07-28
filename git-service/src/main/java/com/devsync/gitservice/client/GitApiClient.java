package com.devsync.gitservice.client;

import com.devsync.gitservice.configuration.GitHubProperties;
import com.devsync.gitservice.model.fromApi.RepositoryFromApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    public String addWebhook(String owner, String repo, Map<String, Object> requestBody, HttpHeaders headers) {
        String url = String.format("https://api.github.com/repos/%s/%s/hooks", owner, repo);

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, headers.getFirst(HttpHeaders.AUTHORIZATION))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String response = webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(body -> System.out.println(body))
                .doOnError(error -> System.err.println(error.getMessage()))
                .block();

        return response;
    }
}
