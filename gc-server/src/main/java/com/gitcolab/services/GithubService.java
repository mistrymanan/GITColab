package com.gitcolab.services;

import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.dto.GithubAuthTokenResponse;
import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class GithubService {
    Logger logger= LoggerFactory.getLogger(GithubService.class);
    ToolTokenManagerRepository integrationRepository;
    UserRepository userRepository;
    @Value("${gitcolab.app.github.clientId}")
    private String CLIENT_ID;

    @Value("${gitcolab.app.github.clientSecret}")
    private String CLIENT_SECRET;

    @Autowired
    public GithubService(ToolTokenManagerRepository integrationRepository, UserRepository userRepository) {
        this.integrationRepository = integrationRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAccessToken(GithubAuthRequest githubAuthRequest) {
        if (githubAuthRequest.getCode() == null || githubAuthRequest.getCode().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github auth code is invalid."));
        }
        if(githubAuthRequest.getEmail() == null || githubAuthRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github auth code is invalid."));
        }

        StringBuilder githubAuthUrl = new StringBuilder("https://github.com/login/oauth/access_token");
        githubAuthUrl.append("?client_id=" + CLIENT_ID);
        githubAuthUrl.append("&client_secret=" + CLIENT_SECRET);
        githubAuthUrl.append("&code=" + githubAuthRequest.getCode());

        WebClient webClient2 = WebClient.builder()
                .baseUrl(String.valueOf(githubAuthUrl))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        String response = webClient2.post().retrieve().bodyToMono(String.class).block();
        if(!(response.startsWith("access_token") || response.startsWith("error"))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        if(response.startsWith("error")) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        String[] splitResponse = response.split("&");
        String accessToken = splitResponse[0].split("=")[1];
        String type = splitResponse[2].split("=")[1];

        Optional<User> user = userRepository.getUserByEmail(githubAuthRequest.getEmail());
        Optional<ToolTokenManager> github = integrationRepository.getByEmail(githubAuthRequest.getEmail(),EnumIntegrationType.GITHUB);
        if(!user.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        if(!github.isPresent()) {
            integrationRepository.save(new ToolTokenManager(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        } else {
            integrationRepository.update(new ToolTokenManager(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        }

        return ResponseEntity.ok(new GithubAuthTokenResponse(accessToken, type));
    }

    public ResponseEntity<?> generateRepository(GithubRepositoryRequest githubRepositoryRequest) {
        ResponseEntity sanitizationResponse = sanitizeGithubRepositoryRequest(githubRepositoryRequest);
        if(sanitizationResponse != null) return sanitizationResponse;
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(githubRepositoryRequest.getGithubAccessToken()).build();
            if(getRepositoryByName(githubRepositoryRequest.getRepositoryName(),
                    githubRepositoryRequest.getGithubAccessToken()) != null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
            }
            GHCreateRepositoryBuilder repo = github.createRepository(githubRepositoryRequest.getRepositoryName());
            GHRepository created = repo.create();
            return ResponseEntity.badRequest().body(new MessageResponse("Github repository created."));
        } catch (Exception e) {
            logger.error("Github facing issue."+e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Github facing issue."));
        }
    }

    public boolean generateRepository(String name, String token) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            if(getRepositoryByName(name, token) != null) {
                return false;
            }
            GHCreateRepositoryBuilder repo = github.createRepository(name);
            GHRepository created = repo.create();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public GHRepository getRepositoryByName(String name, String token) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            GHRepository repository = github.getRepository(name);
            return repository;
        } catch (Exception e) {
            return null;
        }
    }

    private ResponseEntity<MessageResponse> sanitizeGithubRepositoryRequest(GithubRepositoryRequest githubRepositoryRequest) {
        if(githubRepositoryRequest == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github repository request is empty"));
        }
        if(!HelperUtils.isValidString(githubRepositoryRequest.getGithubAccessToken()))
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        if(!HelperUtils.isValidString(githubRepositoryRequest.getRepositoryName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Repository name is empty."));
        return null;
    }
}
