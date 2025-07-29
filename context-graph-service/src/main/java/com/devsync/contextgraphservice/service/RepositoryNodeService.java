package com.devsync.contextgraphservice.service;

import com.devsync.contextgraphservice.entity.RepositoryNode;

import java.util.List;

public interface RepositoryNodeService {
    List<RepositoryNode> getByUser(String username);
}
