package com.devsync.authservice.service;

import java.util.Map;

public interface GitHubOAuthService {
    String getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);
    String getGithubAccessToken(String username);
    void saveGithubToken(String username, String accessToken, String code);
}
