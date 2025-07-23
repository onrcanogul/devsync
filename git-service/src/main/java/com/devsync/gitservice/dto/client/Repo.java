package com.devsync.gitservice.dto.client;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Repo {
    private long id;
    private String name;
    private Owner owner;
}
