package com.gitcolab.dro.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueData {
    private String url;
    private String title;
    private String body;
    private AtlassianUser user;
}
