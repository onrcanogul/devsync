package com.devsync.authservice.controller;

import com.devsync.authservice.model.dto.LoginDto;
import com.devsync.authservice.model.dto.RegisterDto;
import com.devsync.authservice.model.response.AuthResponse;
import com.devsync.authservice.service.AuthService;
import com.devsync.authservice.service.GitHubOAuthService;
import com.devsync.authservice.service.JwtService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final GitHubOAuthService gitHubOAuthService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, GitHubOAuthService gitHubOAuthService, JwtService jwtService) {
        this.authService = authService;
        this.gitHubOAuthService = gitHubOAuthService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto model) {
        return ResponseEntity.ok(authService.login(model));
    }

    @PostMapping("/refresh-token-login/{refreshToken}")
    public ResponseEntity<AuthResponse> login(@PathVariable String refreshToken) {
        return ResponseEntity.ok(authService.loginWithRefreshToken(refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto model) {
        authService.register(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/github/code")
    public ResponseEntity<?> handleGitHubLogin(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        String accessToken = gitHubOAuthService.getAccessToken(code);
        Map<String, Object> userInfo = gitHubOAuthService.getUserInfo(accessToken);

        String githubId = userInfo.get("id").toString();
        String username = userInfo.get("login").toString();
        String email = userInfo.get("email") != null ? userInfo.get("email").toString() : username + "@github.com";

        String jwt = jwtService.generateToken(username, githubId, email);

        return ResponseEntity.ok(Map.of("token", jwt));
    }


}
