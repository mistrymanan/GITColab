package com.gitcolab.controllers;

import com.gitcolab.dro.project.GithubIssueEvent;
import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dto.ContributorRequest;
import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.services.GithubService;
import com.gitcolab.services.ProjectService;
import com.gitcolab.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    Logger logger= LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    GithubService githubService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/generateProject")
    public ResponseEntity<?> generateProject(@Valid @RequestBody GithubRepositoryRequest githubRepositoryRequest) {
        return githubService.generateRepository(githubRepositoryRequest);
    }

    @PostMapping(value = "/createJira",headers="Accept=application/json")
    public ResponseEntity<?> generateJira(@RequestBody GithubIssueEvent createJiraRequest) {
        logger.info("Started JIRA Creation Flow");
        return projectService.createJira(createJiraRequest);
    }

    @PostMapping("/createProject")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectCreationRequest projectCreationRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return projectService.createProject(projectCreationRequest, userDetails);
    }

    @GetMapping("/getProjects")
    public ResponseEntity<?> getAllProjects(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return projectService.getAllProjects(userDetails);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable("projectId") int projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping("/{projectId}/contributors")
    public ResponseEntity<?> getContributors(@PathVariable("projectId") int projectId) {
        return ResponseEntity.ok(projectService.getContributors(projectId));
    }

    @PostMapping("/addContributor")
    public ResponseEntity<?> addContributor(@Valid @RequestBody ContributorRequest contributorRequest) {
        return projectService.addContributor(contributorRequest);
    }
}
