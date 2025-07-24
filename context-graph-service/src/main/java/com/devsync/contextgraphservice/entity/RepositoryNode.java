package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Repository")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryNode {
    @Id
    private Long id;
    private String name;
    private String fullName;
    private String htmlUrl;
    private String visibility;
    private String language;
    private String description;
    private String defaultBranch;
    private String ownerLogin;
    private Long ownerId;
}
