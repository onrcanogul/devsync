package com.devsync.gitservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommitDto {
    private String sha;
    private String message;
    private String date;

    public CommitDto(String sha, String message, String date) {
        this.sha = sha;
        this.message = message;
        this.date = date;
    }

    public String getSha() { return sha; }
    public String getMessage() { return message; }
    public String getDate() { return date; }

    public void setSha(String sha) { this.sha = sha; }
    public void setMessage(String message) { this.message = message; }
    public void setDate(String date) { this.date = date; }
}
