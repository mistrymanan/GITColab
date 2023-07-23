package com.gitcolab.services;

import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dro.project.ProjectCreationResponse;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.Project;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import org.kohsuke.github.GitHub;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;

@Service
public class ProjectService {
    ToolTokenManagerRepository integrationRepository;
    ProjectRepository projectRepository;

    GithubService githubService;

    public ResponseEntity<?> createProject(ProjectCreationRequest projectCreationRequest, UserDetailsImpl userDetails) {
        String repositoryName = projectCreationRequest.getRepositoryName();
        String githubToken = projectCreationRequest.getGithubToken();
        if(githubService.getRepositoryByName(repositoryName, githubToken) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
        }
        // TODO: Handle JIRA board exists check using atlassian token and jira board name

        if(githubService.generateRepository(repositoryName, githubToken)) {
            try {
                GitHub gitHub = githubService.getGithubUserByToken(githubToken);
                if(gitHub == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("User not exists."));
                Project project = new Project(
                        Math.toIntExact(userDetails.getId()),
                        repositoryName,
                        gitHub.getMyself().getLogin(),
                        "", // TODO: Handle Atlassian project ID
                        projectCreationRequest.getJiraBoardName(),
                        Instant.now());
                if(projectCreationRequest.isAtlassianRequired()) {
                    githubService.generateWebHook(repositoryName, githubToken);
                }
                projectRepository.save(project);
                return ResponseEntity.badRequest().body(new ProjectCreationResponse(repositoryName, projectCreationRequest.getJiraBoardName()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
    }
}
