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
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PullRequestNode {
    @Id
    private Long id;
    private Long repoId;
    private String repoName;
    private String title;
    private String branch;

    @Relationship(type = "SOLVES", direction = Relationship.Direction.OUTGOING)
    private List<IssueNode> solves;

    @Relationship(type = "HAS_COMMIT", direction = Relationship.Direction.OUTGOING)
    private List<CommitNode> commits;

    @Relationship(type = "HAS_ANALYSIS", direction = Relationship.Direction.OUTGOING)
    private PullRequestAnalysisNode analysis;
}
