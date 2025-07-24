package com.devsync.contextgraphservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserNode {
    @Id
    private Long githubId;
    private String username;
    private String avatarUrl;
    private String email;
    private String userType;
}
