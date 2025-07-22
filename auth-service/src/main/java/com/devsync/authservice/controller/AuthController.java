package com.devsync.authservice.controller;

import com.devsync.authservice.model.dto.LoginDto;
import com.devsync.authservice.model.dto.RegisterDto;
import com.devsync.authservice.model.response.AuthResponse;
import com.devsync.authservice.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto model) {
        return ResponseEntity.ok(authService.login(model));
    }

    @PostMapping("/refresh-token-login/{refreshToken}")
    public ResponseEntity<AuthResponse> login(@PathVariable String refreshToken) throws Exception {
        return ResponseEntity.ok(authService.loginWithRefreshToken(refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto model) throws BadRequestException {
        authService.register(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
