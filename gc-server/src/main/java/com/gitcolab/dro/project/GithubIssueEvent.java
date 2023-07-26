package com.gitcolab.dro.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubIssueEvent {
    private String action;
    private IssueData issue;
    private RepositoryData repository;
}

