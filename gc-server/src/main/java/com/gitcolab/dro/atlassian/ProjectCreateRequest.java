package com.gitcolab.dro.atlassian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {
    final private String assigneeType="UNASSIGNED";
    private String description;
    private String key;
    private String leadAccountId;
    private String name;
    final static String projectTemplateKey="com.atlassian.jira-core-project-templates:jira-core-simplified-process-control";
    final static String projectTypeKey="software";
    final static String url="http://atlassian.com";

}