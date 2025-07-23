package com.devsync.analyzeservice.repository;

import com.devsync.analyzeservice.entity.CommitAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommitAnalyzeRepository extends JpaRepository<CommitAnalyze, UUID> {
}
