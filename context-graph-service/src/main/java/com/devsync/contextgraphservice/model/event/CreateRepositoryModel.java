package com.devsync.contextgraphservice.model.event;


import com.devsync.contextgraphservice.model.viewmodel.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateRepositoryModel implements Serializable {
    private Repository repository;
}
