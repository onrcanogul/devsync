package com.devsync.gitservice.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "http://localhost:8082")
public interface AuthServiceClient {
    @PostMapping("/api/auth/github/token/{username}")
    String getGitHubAccessToken(@PathVariable String username);
}
