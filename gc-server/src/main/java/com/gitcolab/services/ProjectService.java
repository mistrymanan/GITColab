package com.gitcolab.services;

import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dro.project.ProjectCreationResponse;
import com.gitcolab.dto.ContributorRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.dto.RegisterUserRequest;
import com.gitcolab.dto.UserDTO;
import com.gitcolab.entity.Project;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.utilities.EmailSender;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProjectService {
    ToolTokenManagerRepository integrationRepository;
    ProjectRepository projectRepository;

    GithubService githubService;
    UserService userService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    public ProjectService(ToolTokenManagerRepository integrationRepository, GithubService githubService, ProjectRepository projectRepository, UserService userService) {
        this.integrationRepository = integrationRepository;
        this.githubService = githubService;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

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
