package com.gitcolab.controllers;

import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.services.GithubService;
import com.gitcolab.services.ProjectService;
import com.gitcolab.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    GithubService githubService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/generateProject")
    public ResponseEntity<?> generateProject(@Valid @RequestBody GithubRepositoryRequest githubRepositoryRequest) {
        return githubService.generateRepository(githubRepositoryRequest);
    }

    @PostMapping("/createJira")
    public ResponseEntity<?> generateJira(@Valid @RequestBody Object createJiraRequest) {
        System.out.println("JIRA REQUEST====>" + createJiraRequest);
        return null;
    }

    @PostMapping("/createProject")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectCreationRequest projectCreationRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return projectService.createProject(projectCreationRequest, userDetails);
    }
}
