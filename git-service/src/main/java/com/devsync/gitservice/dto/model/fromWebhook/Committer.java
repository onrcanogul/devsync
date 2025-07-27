package com.devsync.gitservice.dto.model.fromWebhook;

import lombok.Data;

@Data
public class Committer {
    private String name;
    private String email;
    private String username;
}
