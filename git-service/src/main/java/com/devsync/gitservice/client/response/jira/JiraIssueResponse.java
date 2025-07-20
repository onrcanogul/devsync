package com.devsync.gitservice.client.response.jira;

import lombok.Data;

@Data
public class JiraIssueResponse {
    private String key;
    private Fields fields;

    @Data
    public static class Fields {
        private String summary;
        private String description;
        private IssueType issuetype;
    }

    @Data
    public static class IssueType {
        private String name; // e.g. "Bug", "Story"
    }
}
