package com.devsync.authservice.service;

import java.util.Map;

public interface GitHubOAuthService {
    String getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);
}
