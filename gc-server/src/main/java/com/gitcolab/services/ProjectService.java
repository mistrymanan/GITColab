package com.gitcolab.services;

import com.gitcolab.dro.project.GithubIssueEvent;
import com.gitcolab.dro.atlassian.*;
import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dro.project.ProjectCreationResponse;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Project;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
public class ProjectService {
    Logger logger= LoggerFactory.getLogger(ProjectService.class);
    ToolTokenManagerRepository toolTokenManagerRepository;
    ProjectRepository projectRepository;

    GithubService githubService;
    AtlassianService atlassianService;

    @Autowired
    public ProjectService(ToolTokenManagerRepository toolTokenManagerRepository, GithubService githubService, ProjectRepository projectRepository,AtlassianService atlassianService) {
        this.toolTokenManagerRepository = toolTokenManagerRepository;
        this.githubService = githubService;
        this.projectRepository = projectRepository;
        this.atlassianService=atlassianService;
    }

    public ResponseEntity<?> createProject(ProjectCreationRequest projectCreationRequest, UserDetailsImpl userDetails) {
        String repositoryName = projectCreationRequest.getRepositoryName();
        String githubToken = projectCreationRequest.getGithubToken();
        String atlassianProjectId="-1";

        if(githubService.getRepositoryByName(repositoryName, githubToken) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
        }
        if(projectRepository.isJiraBoardExist(projectCreationRequest.getJiraBoardName(),Long.toString(userDetails.getId()))){
            return ResponseEntity.badRequest().body(new MessageResponse("Jira Board With Given Name Already Exists"));
        }
        if(projectCreationRequest.isAtlassianRequired()){
            if (projectCreationRequest.getAtlassianToken() == null || projectCreationRequest.getAtlassianToken().isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Atlassian auth code is invalid."));
            }
            ResponseEntity<?> projectResponse=atlassianService.createAtlassianProject(projectCreationRequest);
            if(projectResponse.getStatusCode()== HttpStatus.CREATED){
                try {
                    ProjectResponse projectResp= ProjectResponse.fromJson(projectResponse.getBody().toString());
                    atlassianProjectId=projectResp.getId();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        if(githubService.generateRepository(repositoryName, githubToken)) {
            try {
                GitHub gitHub = githubService.getGithubUserByToken(githubToken);
                if(gitHub == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("User not exists."));
                Project project = new Project(
                        Math.toIntExact(userDetails.getId()),
                        repositoryName,
                        gitHub.getMyself().getLogin(),
                        atlassianProjectId,
                        projectCreationRequest.getJiraBoardName(),
                        Instant.now());
                if(projectCreationRequest.isAtlassianRequired()) {
                    githubService.generateWebHook(repositoryName, githubToken);
                }
                projectRepository.save(project);
                return ResponseEntity.ok().body(new ProjectCreationResponse(repositoryName, projectCreationRequest.getJiraBoardName()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
    }

    public ResponseEntity<?> createJira(GithubIssueEvent githubIssueEvent) {

        String[] githubRepoSegments = githubIssueEvent.getRepository().getFull_name().split("/");
        String githubUserName=githubRepoSegments[0];
        String repositoryName=githubRepoSegments[1];
        Optional<ToolTokenManager> toolTokenManager=toolTokenManagerRepository.getByRepositoryOwner(githubUserName, EnumIntegrationType.ATLASSIAN);
        Optional<Project> project= projectRepository.getProjectByRepositoryNameAndOwner(repositoryName,githubUserName);
        if(!toolTokenManager.isEmpty()){
            String atlassianToken="Bearer "+toolTokenManager.get().getToken();
            atlassianService.createIssue(githubIssueEvent,project.get().getAtlassianProjectId(),atlassianToken);
        }
        return null;
    }
}
