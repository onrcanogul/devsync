package com.devsync.contextgraphservice.repository;

import com.devsync.contextgraphservice.entity.PullRequestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PullRequestRepository extends Neo4jRepository<PullRequestNode, Long> {
    List<PullRequestNode> findByBranch(String branch);
    @Query("MATCH (pr:PullRequest)-[:SOLVES]->(i:Issue) WHERE i.projectKey = $key RETURN pr")
    List<PullRequestNode> findByProjectKey(@Param("key") String key);
}
