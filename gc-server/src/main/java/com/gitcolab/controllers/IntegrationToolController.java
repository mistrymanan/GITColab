package com.gitcolab.controllers;

import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.services.GithubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/github")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class GithubController {

    @Autowired
    GithubService githubService;

    @PostMapping("/getAccessToken")
    public ResponseEntity<?> getGithubAccessToken(@Valid @RequestBody GithubAuthRequest githubAuthRequest) {
        return githubService.getAccessToken(githubAuthRequest);
    }

    @PostMapping("/generateRepo")
    public ResponseEntity<?> generateRepository(@Valid @RequestBody GithubRepositoryRequest githubRepositoryRequest) {
        return githubService.generateRepository(githubRepositoryRequest);
    }
}
