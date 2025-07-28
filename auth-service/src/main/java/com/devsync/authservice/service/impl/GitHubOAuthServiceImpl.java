package com.devsync.authservice.service.impl;

import com.devsync.authservice.model.entity.GithubToken;
import com.devsync.authservice.repository.GithubTokenRepository;
import com.devsync.authservice.service.GitHubOAuthService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GitHubOAuthServiceImpl implements GitHubOAuthService {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    private GithubTokenRepository githubTokenRepository;

    public GitHubOAuthServiceImpl(GithubTokenRepository githubTokenRepository) {
        this.githubTokenRepository = githubTokenRepository;
    }

    public String getAccessToken(String code) {
        String url = "https://github.com/login/oauth/access_token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HashMap<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);
        body.put("redirect_uri", "http://localhost:3000/oauth/callback");

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return (String) response.getBody().get("access_token");
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        String url = "https://api.github.com/user";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }

    public String getGithubAccessToken(String username) {
        GithubToken token = githubTokenRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return token.getToken();
    }

    public void saveGithubToken(String username, String accessToken, String code) {
        Optional<GithubToken> githubToken = githubTokenRepository.findByUsername(username);
        if (githubToken.isPresent()) {
            GithubToken token = githubToken.get();
            token.setToken(accessToken);
            githubTokenRepository.save(token);
        }
        GithubToken newGithubToken = new GithubToken();
        newGithubToken.setCode(code);
        newGithubToken.setUsername(username);
        newGithubToken.setToken(accessToken);
        githubTokenRepository.save(newGithubToken);
    }
}