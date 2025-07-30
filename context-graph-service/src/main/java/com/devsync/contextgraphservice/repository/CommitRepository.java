package com.devsync.contextgraphservice.repository;

import com.devsync.contextgraphservice.entity.CommitNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository extends Neo4jRepository<CommitNode, String> {
    CommitNode findByHash(String hash);
}
