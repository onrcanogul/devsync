package com.devsync.contextgraphservice.repository;

import com.devsync.contextgraphservice.entity.PullRequestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PullRequestRepository extends Neo4jRepository<PullRequestNode, Long> {
    List<PullRequestNode> findByBranch(String branch);
    List<PullRequestNode> findAllByRepositoryId(Long repositoryId);
    List<PullRequestNode> findByBranchAndRepository_Id(String branch, Long repository_id);
    List<PullRequestNode> findByRepository_OwnerLogin(String ownerLogin);
    @Query("MATCH (pr:PullRequest)-[:SOLVES]->(i:Issue) WHERE i.projectKey = $key RETURN pr")
    List<PullRequestNode> findByProjectKey(@Param("key") String key);
}
