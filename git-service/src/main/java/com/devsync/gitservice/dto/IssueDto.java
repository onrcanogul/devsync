package com.devsync.gitservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueDto {
    private String key;
    private String summary;
    private String description;
    private String issueType;
}
