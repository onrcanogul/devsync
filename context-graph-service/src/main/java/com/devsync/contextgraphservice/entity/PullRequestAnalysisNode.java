package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("PullRequestAnalysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PullRequestAnalysisNode {
    @Id
    private Long id;

    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;
    private Integer riskScore;
}
