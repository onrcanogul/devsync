package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("PullRequest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PullRequestNode {
    @Id
    private Long id;
    private String branch;
    private String pusher;
    private String headCommitMessage;
    private String headCommitSha;
    private int commitCount;

    @Relationship(type = "HAS_COMMIT", direction = Relationship.Direction.OUTGOING)
    private List<CommitNode> commits;

    @Relationship(type = "HAS_ANALYSIS", direction = Relationship.Direction.OUTGOING)
    private PullRequestAnalysisNode analysis;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private UserNode createdBy;

    @Relationship(type = "SOLVES", direction = Relationship.Direction.OUTGOING)
    private List<IssueNode> solves;

    @Relationship(type = "IN_REPOSITORY", direction = Relationship.Direction.OUTGOING)
    private RepositoryNode repository;
}


