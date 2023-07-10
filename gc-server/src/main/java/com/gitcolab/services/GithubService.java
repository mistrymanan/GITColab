package com.gitcolab.services;

import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.dto.GithubAuthTokenResponse;
import com.gitcolab.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;

@Service
public class GithubService {
    @Value("${gitcolab.app.github.clientId}")
    private String CLIENT_ID;

    @Value("${gitcolab.app.github.clientSecret}")
    private String CLIENT_SECRET;
    public ResponseEntity<?> getAccessToken(GithubAuthRequest githubAuthRequest) {
        if (githubAuthRequest.getCode() == null || githubAuthRequest.getCode().isEmpty()) {
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
        return ResponseEntity.ok(new GithubAuthTokenResponse(accessToken, type));
    }
}
