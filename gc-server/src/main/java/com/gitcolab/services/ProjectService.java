package com.gitcolab.services;

import com.gitcolab.dro.project.GithubIssueEvent;
import com.gitcolab.dro.atlassian.*;
import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dro.project.ProjectCreationResponse;
import com.gitcolab.dto.ContributorRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.dto.RegisterUserRequest;
import com.gitcolab.dto.UserDTO;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Project;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.utilities.EmailSender;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Service
public class ProjectService {
    Logger logger= LoggerFactory.getLogger(ProjectService.class);
    ToolTokenManagerRepository toolTokenManagerRepository;
    ProjectRepository projectRepository;

    GithubService githubService;
    UserService userService;

    @Autowired
    EmailSender emailSender;
    AtlassianService atlassianService;

    @Autowired
    public ProjectService(ToolTokenManagerRepository toolTokenManagerRepository, GithubService githubService, ProjectRepository projectRepository,AtlassianService atlassianService,UserService userService) {
        this.toolTokenManagerRepository = toolTokenManagerRepository;
        this.githubService = githubService;
        this.projectRepository = projectRepository;
        this.atlassianService=atlassianService;
        this.userService = userService;
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
                    ProjectResponse projectResp = ProjectResponse.fromJson(projectResponse.getBody().toString());
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
                Optional<Project> createdProject = projectRepository.findByRepositoryName(repositoryName);
                projectRepository.addContributor(Math.toIntExact(userDetails.getId()), createdProject.get().getId());
                return ResponseEntity.ok().body(new ProjectCreationResponse(repositoryName, projectCreationRequest.getJiraBoardName()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
    }

    public ResponseEntity<?> getAllProjects(UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(projectRepository.getAllProject(userDetails.getId()));
    }

    public Optional<Project> getProjectById(int projectId) {
        return projectRepository.findById(projectId);
    }

    public ResponseEntity<List<Map<String, Object>>> getContributors(int projectId) {
        return ResponseEntity.ok().body(projectRepository.getAllContributors(projectId));
    }

    public ResponseEntity<?> addContributor(ContributorRequest contributorRequest) {
        if(!HelperUtils.isValidString(contributorRequest.getUsername()) || !HelperUtils.isValidString(contributorRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid request"));

        if(!HelperUtils.isValidString(contributorRequest.getGithubAuthToken()) || !HelperUtils.isValidString(contributorRequest.getRepositoryName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid github information"));

        GHRepository ghRepository = githubService.getRepositoryByName(contributorRequest.getRepositoryName(), contributorRequest.getGithubAuthToken());
        if(ghRepository == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository not exists"));
        }
        boolean isContributorAdded = githubService.addCollaborator(
                contributorRequest.getRepositoryName(),
                contributorRequest.getUsername(),
                contributorRequest.getGithubAuthToken()
        );
        if(!isContributorAdded)
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));

        String password = HelperUtils.generateRandomPassword(8);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                contributorRequest.getUsername(),
                contributorRequest.getEmail(),
                password
        );
        ResponseEntity response = userService.registerUser(registerUserRequest);
        Optional<Project> project = projectRepository.findByRepositoryName(contributorRequest.getRepositoryName());
        UserDTO user = userService.getUser(registerUserRequest.getUsername());
        projectRepository.addContributor((int) user.getId(), project.get().getId());

        StringBuilder message = new StringBuilder("");
        message.append("Hello " + registerUserRequest.getUsername() + ",\n\n");
        message.append("Welcome to GitColab!\n");
        message.append("Your username: " + registerUserRequest.getUsername() + "\n");
        message.append("Your password: " + registerUserRequest.getPassword() + "\n\n");
        message.append("Please change the password while logging first time.\n\n");
        message.append("Team GitColab!");
        emailSender.sendEmail(registerUserRequest.getEmail(),
                "Welcome to GitColab",
                String.valueOf(message));
        return ResponseEntity.ok().body(new MessageResponse("Contributor added successfully"));
    }

    public ResponseEntity<?> createJira(GithubIssueEvent githubIssueEvent) {
        String[] githubRepoSegments = githubIssueEvent.getRepository().getFull_name().split("/");
        String githubUserName=githubRepoSegments[0];
        String repositoryName=githubRepoSegments[1];
        Optional<ToolTokenManager> toolTokenManager=toolTokenManagerRepository.getByRepositoryOwner(githubUserName, EnumIntegrationType.ATLASSIAN);
        logger.info("Data From Tool Token Manager->"+toolTokenManager);
        logger.info(toolTokenManager.toString());
        Optional<Project> project= projectRepository.getProjectByRepositoryNameAndOwner(repositoryName,githubUserName);
        logger.info("Data From Project -> "+project);
        if(!toolTokenManager.isEmpty()){
            String atlassianToken="Bearer "+toolTokenManager.get().getToken();
            atlassianService.createIssue(githubIssueEvent,project.get().getAtlassianProjectId(),atlassianToken);
        }
        return null;
    }

    public ResponseEntity<?> getProjectContributorMap(int level) {
        if(level <= 0)
            return ResponseEntity.ok().body(new MessageResponse("Invalid level"));
        List<Map<String, Object>> projectContributorMap = projectRepository.getProjectContributorMap();
        Map<Integer, Set<Integer>> adjacencyList = generateAdjacencyList(projectContributorMap);
        // TODO: Handling Explore by depth
        return ResponseEntity.ok().body(projectRepository.getProjectContributorMap());
    }

    private Map<Integer, Set<Integer>> generateAdjacencyList(List<Map<String, Object>> projectContributorMap) {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (Map<String, Object> project : projectContributorMap) {
            int projectId = (int) project.get("projectId");
            int contributorId = (int) project.get("userId");

            if (!adjacencyList.containsKey(projectId)) {
                adjacencyList.put(projectId, new HashSet<>());
            }
            adjacencyList.get(projectId).add(contributorId);
        }
        return adjacencyList;
    }

    public ResponseEntity<?> getDashboardData(long userId) {
        Map<String, Object> dashboardData = new HashMap<>();
        List<Map<String, Object>> allProjects = projectRepository.getAllProject(userId);
        AtomicInteger totalProjectOwnership = new AtomicInteger();
        AtomicInteger totalProjectContributions = new AtomicInteger();
        allProjects.stream().forEach(stringObjectMap -> {
            AtomicInteger userId1 = new AtomicInteger(((int)stringObjectMap.get("userId") == (int) userId) ?
                    totalProjectOwnership.getAndIncrement() : totalProjectContributions.getAndIncrement());
        });
        dashboardData.put("totalProjectOwnership", totalProjectOwnership);
        dashboardData.put("totalProjectContributions", totalProjectContributions);
        String githubAuthToken = projectRepository.getGithubTokenByUserId(userId);
        GitHub gitHub = githubService.getGithubUserByToken(githubAuthToken);
        try {
            dashboardData.put("numberOfFollowers", gitHub.getMyself().getFollowersCount());
            dashboardData.put("totalRepositories", gitHub.getMyself().getPublicRepoCount());
            dashboardData.put("topCommittedRepositories", githubService.topCommittedRepositories(gitHub.getMyself(), 10));
        } catch (Exception e) {
            dashboardData.put("numberOfFollowers", 0);
            dashboardData.put("totalRepositories", 0);
            dashboardData.put("topCommittedRepositories", "");
        }
        return ResponseEntity.ok().body(dashboardData);
    }
}
