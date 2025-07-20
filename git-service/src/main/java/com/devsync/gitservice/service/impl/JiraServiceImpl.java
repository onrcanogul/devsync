package com.devsync.gitservice.service.impl;

import com.devsync.gitservice.client.JiraClient;
import com.devsync.gitservice.client.response.jira.JiraIssueResponse;
import com.devsync.gitservice.dto.IssueDto;
import com.devsync.gitservice.dto.PullRequestDto;
import com.devsync.gitservice.service.JiraService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JiraServiceImpl implements JiraService {
    private final JiraClient jiraClient;

    public JiraServiceImpl(JiraClient jiraClient) {
        this.jiraClient = jiraClient;
    }

    public void enrichPullRequestWithJiraIssues(PullRequestDto prDto) {
        List<String> issueKeys = extractIssueKeysFromText(prDto.getTitle() + " " + prDto.getBranch());
        List<IssueDto> issues = new ArrayList<>();

        for (String key : issueKeys) {
            JiraIssueResponse issue = jiraClient.getIssue(key); // Client içinde mapping var
            if (issue != null) {
                IssueDto dto = new IssueDto();
                dto.setKey(issue.getKey());
                dto.setDescription(issue.getFields().getDescription());
                dto.setSummary(issue.getFields().getSummary());
                dto.setIssueType(issue.getFields().getIssuetype().getName());
                issues.add(dto);
            }
        }
        prDto.setIssues(issues);
    }

    private List<String> extractIssueKeysFromText(String text) {
        List<String> keys = new ArrayList<>();
        Pattern pattern = Pattern.compile("[A-Z]+-\\d+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            keys.add(matcher.group());
        }
        return keys;
    }
}
