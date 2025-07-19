package com.devsync.analyzeservice.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiffDto {
    private List<ChangedFileDto> changedFiles;

    public DiffDto(List<ChangedFileDto> changedFiles) {
        this.changedFiles = changedFiles;
    }

    public List<ChangedFileDto> getChangedFiles() { return changedFiles; }
    public void setChangedFiles(List<ChangedFileDto> changedFiles) { this.changedFiles = changedFiles; }

    public DiffDto() {

    }
}
