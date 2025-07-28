package com.devsync.authservice.repository;

import com.devsync.authservice.model.entity.GithubToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GithubTokenRepository extends JpaRepository<GithubToken, UUID> {
    Optional<GithubToken> findByUsername(String username);
}
