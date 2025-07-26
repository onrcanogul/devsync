package com.devsync.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateToken(String username, String githubId, String email);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUsername(String token);
    List<String> extractRoles(String token);
}
