package com.gitcolab.services;

import com.gitcolab.configurations.ClientConfig;
import com.gitcolab.dro.atlassian.*;
import com.gitcolab.dro.project.GithubIssueEvent;
import com.gitcolab.dro.project.ProjectCreationRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AtlassianService {

    Logger logger= LoggerFactory.getLogger(AtlassianService.class);

    @Value("${gitcolab.app.atlassian.clientSecret}")
    private String ATLASSIAN_CLIENT_SECRET;
    ToolTokenManagerRepository toolTokenManagerRepository;
    UserRepository userRepository;

    private ClientConfig clientConfig;
    private AtlassianServiceClient atlassianServiceClient;

    private ProjectRepository projectRepository;


    public AtlassianService(ToolTokenManagerRepository toolTokenManagerRepository, UserRepository userRepository, ClientConfig clientConfig,ProjectRepository projectRepository) {
        this.toolTokenManagerRepository = toolTokenManagerRepository;
        this.userRepository = userRepository;
        this.clientConfig=clientConfig;
        this.atlassianServiceClient=clientConfig.atlassianServiceClient();
        this.projectRepository=projectRepository;
    }

    public ResponseEntity<?> getAccessToken(GetAccessTokenRequest getAccessTokenRequest, UserDetailsImpl userDetails){
        if (getAccessTokenRequest.getCode() == null || getAccessTokenRequest.getCode().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Atlassian auth code is invalid."));
        }

        getAccessTokenRequest.setClient_secret(ATLASSIAN_CLIENT_SECRET);

        ResponseEntity<GetAccessTokenResponse> response=atlassianServiceClient.getAccessToken(getAccessTokenRequest);

        Optional<ToolTokenManager> integration = toolTokenManagerRepository.getByEmail(userDetails.getEmail(),EnumIntegrationType.ATLASSIAN);

        if(!integration.isPresent()) {
            toolTokenManagerRepository.save(new ToolTokenManager(EnumIntegrationType.ATLASSIAN, response.getBody().getAccess_token(), userDetails.getId()));
        } else {
            toolTokenManagerRepository.update(new ToolTokenManager(EnumIntegrationType.ATLASSIAN, response.getBody().getAccess_token(), userDetails.getId()));
        }
        return ResponseEntity.ok().body(response);
    }

    public Optional<AccessibleResource> getAccessibleResources(String bearerToken){
        List<AccessibleResource> accessibleResources=atlassianServiceClient.getAccessibleResources(bearerToken);
        if(!accessibleResources.isEmpty()){
            return Optional.of(accessibleResources.get(0));
        }
        return Optional.empty();
    }

    public Optional<MySelfResponse> getUserDetails(String bearerToken, String cloudId){
            return Optional.of(atlassianServiceClient.getUserDetails(cloudId,bearerToken));
    }

    public ResponseEntity<?> createAtlassianProject(ProjectCreationRequest projectCreationRequest){
        String atlassianToken ="Bearer " + projectCreationRequest.getAtlassianToken();

        Optional<AccessibleResource> accessibleResource = getAccessibleResources(atlassianToken);

        if(!accessibleResource.isEmpty()){
            String cloudId = accessibleResource.get().getId();
            Optional<MySelfResponse> mySelfResponse = getUserDetails(atlassianToken,accessibleResource.get().getId());
            if(!mySelfResponse.isEmpty()){
                ProjectCreateRequest projectCreateRequest =
                        new ProjectCreateRequest(
                                projectCreationRequest.getRepositoryName()
                                ,generateKey(projectCreationRequest.getRepositoryName())
                                ,mySelfResponse.get().getAccountId()
                                ,projectCreationRequest.getRepositoryName());
            return atlassianServiceClient.createProject(cloudId,atlassianToken,projectCreateRequest);
            }

        }
        return ResponseEntity.badRequest().body(new MessageResponse("No Atlassian site found!"));
    }

    public ResponseEntity<?> createIssue(GithubIssueEvent githubIssueEvent,String projectId,String bearerToken){
        logger.info("Creating Issue");
        Optional<AccessibleResource> accessibleResource=getAccessibleResources(bearerToken);
        logger.info("Accessible Resource->"+accessibleResource);
        if(!accessibleResource.isEmpty()){
            Optional<MySelfResponse> mySelfResponse=getUserDetails(bearerToken,accessibleResource.get().getId());
            logger.info("MySelfResponse->"+accessibleResource);
            if(!mySelfResponse.isEmpty()){
                CreateIssueRequest createIssueRequest = new CreateIssueRequest(githubIssueEvent,projectId,mySelfResponse.get().getAccountId());
                logger.info("CreateIssueRequest->"+createIssueRequest);
                ResponseEntity<Void> response = atlassianServiceClient.createIssueRequest(accessibleResource.get().getId(),bearerToken,createIssueRequest);
                logger.info("Response From atlassianServiceClient!"+response);
                return response;
//                return atlassianServiceClient.createIssueRequest(accessibleResource.get().getId(),bearerToken,createIssueRequest);
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong while creating JIRA!"));
    }

    public static String generateKey(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null.");
        }

        String[] words = name.split("\\s+");
        String keyword;

        if (words.length == 1) {
            keyword = name.toUpperCase().substring(0, Math.min(name.length(), 5));
        } else {
            keyword = words[0].toUpperCase().substring(0, Math.min(words[0].length(), 5));
        }

        String digits = generateRandomDigits(5);

        return keyword + digits;
    }

    private static String generateRandomDigits(int count) {
        Random random = new Random();
        StringBuilder digits = new StringBuilder();

        for (int i = 0; i < count; i++) {
            digits.append(random.nextInt(10));
        }

        return digits.toString();
    }
}
