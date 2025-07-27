package com.devsync.gitservice.model.fromWebhook;

import lombok.Data;

@Data
public class Author {
    private String name;
    private String email;
    private String username;
}