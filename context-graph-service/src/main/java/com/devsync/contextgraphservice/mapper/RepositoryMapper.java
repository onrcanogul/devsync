package com.devsync.contextgraphservice.mapper;

import com.devsync.contextgraphservice.entity.RepositoryNode;
import com.devsync.contextgraphservice.model.viewmodel.Repository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapper {

    public RepositoryNode toNode(Repository repository) {
        if (repository == null) {
            return null;
        }

        return new RepositoryNode(
                repository.getId(),
                repository.getName(),
                repository.getFull_name(),
                repository.getHtml_url(),
                repository.getVisibility(),
                repository.getLanguage(),
                repository.getDescription(),
                repository.getDefault_branch(),
                repository.getOwner() != null ? repository.getOwner().getLogin() : null,
                repository.getOwner() != null ? repository.getOwner().getId() : null
        );
    }
}