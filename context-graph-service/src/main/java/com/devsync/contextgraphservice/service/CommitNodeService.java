package com.devsync.contextgraphservice.service;

import com.devsync.contextgraphservice.entity.CommitNode;

import java.util.List;

public interface CommitNodeService {
    List<CommitNode> get();
    CommitNode getByHash(String hash);
}
