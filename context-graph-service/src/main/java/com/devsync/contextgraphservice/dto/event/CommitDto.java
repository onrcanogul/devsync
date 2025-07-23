package com.devsync.contextgraphservice.dto.event;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommitDto {
    private String sha;
    private String message;
    private String date;

    public CommitDto(String sha, String message, String date) {
        this.sha = sha;
        this.message = message;
        this.date = date;
    }

    public CommitDto() {

    }
}
