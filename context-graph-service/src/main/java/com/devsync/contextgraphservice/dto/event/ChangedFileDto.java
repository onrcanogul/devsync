package com.devsync.contextgraphservice.dto.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public String getFilename() { return filename; }
    public String getStatus() { return status; }
    public int getAdditions() { return additions; }
    public int getDeletions() { return deletions; }

    public void setFilename(String filename) { this.filename = filename; }
    public void setStatus(String status) { this.status = status; }
    public void setAdditions(int additions) { this.additions = additions; }
    public void setDeletions(int deletions) { this.deletions = deletions; }

    public ChangedFileDto() {

    }
}
