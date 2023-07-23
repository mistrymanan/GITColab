package com.gitcolab.dro.project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreationRequest {
    private String email;
    private String githubToken;
    private String repositoryName;
    private String atlassianToken;
    private boolean isAtlassianRequired;
    private String jiraBoardName;
}
