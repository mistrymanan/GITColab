package com.gitcolab.services;

import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.dto.GithubAuthTokenResponse;
import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.GithubRepository;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GithubService {

    GithubRepository githubRepository;
    UserRepository userRepository;
    @Value("${gitcolab.app.github.clientId}")
    private String CLIENT_ID;

    @Value("${gitcolab.app.github.clientSecret}")
    private String CLIENT_SECRET;

    @Autowired
    public GithubService(GithubRepository githubRepository, UserRepository userRepository) {
        this.githubRepository = githubRepository;
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
        Optional<Integration> github = githubRepository.getByEmail(githubAuthRequest.getEmail());
        if(!user.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        if(!github.isPresent()) {
            githubRepository.save(new Integration(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        } else {
            githubRepository.update(new Integration(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        }

        return ResponseEntity.ok(new GithubAuthTokenResponse(accessToken, type));
    }

    public ResponseEntity<?> generateRepository(GithubRepositoryRequest githubRepositoryRequest) {
        ResponseEntity sanitizationResponse = sanitizeGithubRepositoryRequest(githubRepositoryRequest);
        if(sanitizationResponse != null) return sanitizationResponse;
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(githubRepositoryRequest.getGithubAccessToken()).build();
            GHCreateRepositoryBuilder repo = github.createRepository(githubRepositoryRequest.getRepositoryName());
            GHRepository created = repo.create();
            return ResponseEntity.badRequest().body(new MessageResponse("Github repository created."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Github facing issue."));
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
