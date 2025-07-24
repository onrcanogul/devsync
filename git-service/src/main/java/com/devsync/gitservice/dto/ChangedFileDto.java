package com.devsync.gitservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangedFileDto {
    private String filename;
    private String status;
    private int additions;
    private int deletions;

    public ChangedFileDto(String filename, String status, int additions, int deletions) {
        this.filename = filename;
        this.status = status;
        this.additions = additions;
        this.deletions = deletions;
    }

}
