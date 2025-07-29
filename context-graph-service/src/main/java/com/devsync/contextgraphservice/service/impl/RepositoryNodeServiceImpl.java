package com.devsync.contextgraphservice.service.impl;

import com.devsync.contextgraphservice.entity.RepositoryNode;
import com.devsync.contextgraphservice.repository.RepoRepository;
import com.devsync.contextgraphservice.service.RepositoryNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryNodeServiceImpl implements RepositoryNodeService {

    private final RepoRepository repoRepository;

    public RepositoryNodeServiceImpl(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public List<RepositoryNode> getByUser(String username) {
        return repoRepository.findByOwnerLogin(username);
    }
}
