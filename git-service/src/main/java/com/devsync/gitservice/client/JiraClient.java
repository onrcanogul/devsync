package com.devsync.gitservice.client;

import com.devsync.gitservice.client.response.jira.JiraIssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JiraClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${jira.base-url}")
    private String jiraBaseUrl;

    @Value("${jira.token}")
    private String jiraAuthToken;

    public JiraIssueResponse getIssue(String issueKey) {
        return webClientBuilder
                .baseUrl(jiraBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + jiraAuthToken)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build()
                .get()
                .uri("/rest/api/3/issue/{issueKey}", issueKey)
                .retrieve()
                .bodyToMono(JiraIssueResponse.class)
                .onErrorResume(ex -> {
                    //log ex
                    return Mono.empty();
                })
                .block();
    }
}

