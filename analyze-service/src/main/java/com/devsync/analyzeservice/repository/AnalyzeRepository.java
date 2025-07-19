package com.devsync.analyzeservice.repository;

import com.devsync.analyzeservice.entity.Analyze;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnalyzeRepository extends JpaRepository<Analyze, UUID> {
    Page<Analyze> findAll(Pageable pageable);
    List<Analyze> findByRepository(String repository);
    Optional<Analyze> findByPullRequestId(Long pullRequestId);
}
