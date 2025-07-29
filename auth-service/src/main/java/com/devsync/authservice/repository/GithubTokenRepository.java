package com.devsync.authservice.repository;

import com.devsync.authservice.model.entity.GithubToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GithubTokenRepository extends JpaRepository<GithubToken, UUID> {
    Optional<GithubToken> findByUsername(String username);
}
