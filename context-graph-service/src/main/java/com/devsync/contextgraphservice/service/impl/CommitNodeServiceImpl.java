package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.entity.CommitNode;
import com.devsync.contextgraphservice.repository.CommitRepository;
import com.devsync.contextgraphservice.service.CommitNodeService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommitNodeServiceImpl implements CommitNodeService {

    private final CommitRepository commitRepository;

    public CommitNodeServiceImpl(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Override
    public List<CommitNode> get() {
        return commitRepository.findAll();
    }

    @Override
    public CommitNode getByHash(String hash) {
        return commitRepository.findByHash(hash);
    }
}
