package com.devsync.contextgraphservice.repository;

import com.devsync.contextgraphservice.entity.RepositoryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface RepoRepository extends Neo4jRepository<RepositoryNode, Long> {
    List<RepositoryNode> findByOwnerLogin(String ownerLogin);
}
