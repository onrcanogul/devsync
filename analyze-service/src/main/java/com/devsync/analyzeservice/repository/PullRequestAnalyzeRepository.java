package com.devsync.analyzeservice.repository;

import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PullRequestAnalyzeRepository extends JpaRepository<PullRequestAnalyze, UUID> {
    List<PullRequestAnalyze> findByRepoId(Long repoId);
    Optional<PullRequestAnalyze> findByPullRequestId(Long pullRequestId);
}
