package com.gitcolab.controllers;

import com.gitcolab.dto.GithubRepositoryRequest;
import com.gitcolab.services.GithubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    GithubService githubService;
    @PostMapping("/generateProject")
    public ResponseEntity<?> generateProject(@Valid @RequestBody GithubRepositoryRequest githubRepositoryRequest) {
        return githubService.generateRepository(githubRepositoryRequest);
    }
}
