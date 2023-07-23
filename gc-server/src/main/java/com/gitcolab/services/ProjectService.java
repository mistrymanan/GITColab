package com.gitcolab.services;

import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dro.project.ProjectCreationResponse;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    ToolTokenManagerRepository integrationRepository;

    GithubService githubService;

    public ResponseEntity<?> createProject(ProjectCreationRequest projectCreationRequest) {
        String repositoryName = projectCreationRequest.getRepositoryName();
        String githubToken = projectCreationRequest.getGithubToken();
        if(githubService.getRepositoryByName(repositoryName, githubToken) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
        }
        // TODO: Handle JIRA board exists check using atlassian token and jira board name

        if(githubService.generateRepository(repositoryName, githubToken)) {
            // TODO: call repository for storing data in Project table
            return ResponseEntity.badRequest().body(new ProjectCreationResponse());
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
    }
}
