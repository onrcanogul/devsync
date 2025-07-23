package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("CommitAnalysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommitAnalysisNode {
    @Id
    private String hash;

    private String technicalComment;
    private String functionalComment;
    private String architecturalComment;
    private Integer commitRiskScore;
}
