package com.gitcolab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private String description;
    private int userId;
    private Instant timestamp;
    private String gitHubRepoName;
    private int atlassianProjectId;

}