package com.devsync.authservice.service;

import com.devsync.authservice.model.dto.LoginDto;
import com.devsync.authservice.model.dto.RegisterDto;
import com.devsync.authservice.model.response.AuthResponse;
import org.apache.coyote.BadRequestException;

public interface AuthService {
    AuthResponse login(LoginDto request);
    AuthResponse loginWithRefreshToken(String refreshToken);
    void register(RegisterDto request);
}
