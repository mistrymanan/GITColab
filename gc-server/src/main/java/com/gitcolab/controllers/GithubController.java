package com.gitcolab.controllers;

import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.services.GithubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/github")
public class GithubController {

    @Autowired
    GithubService githubService;
    @PostMapping("/getAccessToken")
    public ResponseEntity<?> getGithubAccessToken(@Valid @RequestBody GithubAuthRequest githubAuthRequest) {
        return githubService.getAccessToken(githubAuthRequest);
    }
}
