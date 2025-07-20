package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Commit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CommitNode {
    @Id
    private String hash;
    private String message;
}

