package com.devsync.authservice.service;

import com.devsync.authservice.model.dto.LoginDto;
import com.devsync.authservice.model.dto.RegisterDto;
import com.devsync.authservice.model.entity.RefreshToken;
import com.devsync.authservice.model.entity.User;
import com.devsync.authservice.model.response.AuthResponse;
import com.devsync.authservice.repository.RefreshTokenRepository;
import com.devsync.authservice.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(AuthenticationManager authManager, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RefreshTokenRepository refreshTokenRepository, CustomUserDetailsService customUserDetailsService) {
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public AuthResponse login(LoginDto request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user);
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setUser(userRepository.findByUsername(request.getUsername()).orElseThrow());
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiration(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);
        return new AuthResponse(token, refreshTokenEntity.getToken());
    }

    public AuthResponse loginWithRefreshToken(String refreshToken) throws Exception {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                Exception::new);
        if (refreshTokenEntity.getExpiration().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("RefreshTokenExpired");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(refreshTokenEntity.getUser().getUsername());
        String accessToken = jwtService.generateToken(userDetails);
        refreshTokenEntity.setToken(UUID.randomUUID().toString());
        refreshTokenEntity.setExpiration(LocalDateTime.now().plusDays(7));
        RefreshToken updatedRefreshToken = refreshTokenRepository.save(refreshTokenEntity);
        return new AuthResponse(accessToken, updatedRefreshToken.getToken());
    }

    public void register(RegisterDto request) throws BadRequestException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already taken.");
        }
        User user = new User();
        user.setRoles(List.of("USER"));
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
