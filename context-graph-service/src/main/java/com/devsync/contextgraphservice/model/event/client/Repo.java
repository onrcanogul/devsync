package com.devsync.contextgraphservice.model.event.client;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Repo {
    private long id;
    private String name;
    private Owner owner;
}
