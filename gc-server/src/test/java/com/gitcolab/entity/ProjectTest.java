package com.gitcolab.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectTest {

    @Test
    public void testAllArgsConstructor() {
        int id = 1;
        String name = "Test Project";
        String description = "This is a test project.";
        int userId = 42;
        Instant timestamp = Instant.now();
        String gitHubRepoName = "test-repo";
        int atlassianProjectId = 100;

        Project project = new Project(id, name, description, userId, timestamp, gitHubRepoName, atlassianProjectId);

        assertEquals(id, project.getId());
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
        assertEquals(userId, project.getUserId());
        assertEquals(timestamp, project.getTimestamp());
        assertEquals(gitHubRepoName, project.getGitHubRepoName());
        assertEquals(atlassianProjectId, project.getAtlassianProjectId());
    }
    @Test
    public void testNoArgsConstructor() {
        Project project = new Project();

        assertEquals(0, project.getId());
        assertNull(project.getName());
        assertNull(project.getDescription());
        assertEquals(0, project.getUserId());
        assertNull(project.getTimestamp());
        assertNull(project.getGitHubRepoName());
        assertEquals(0, project.getAtlassianProjectId());
    }

    @Test
    public void testGettersAndSetters() {
        Project project = new Project();

        int id = 1;
        String name = "Test Project";
        String description = "This is a test project.";
        int userId = 42;
        Instant timestamp = Instant.now();
        String gitHubRepoName = "test-repo";
        int atlassianProjectId = 100;

        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        project.setUserId(userId);
        project.setTimestamp(timestamp);
        project.setGitHubRepoName(gitHubRepoName);
        project.setAtlassianProjectId(atlassianProjectId);

        assertEquals(id, project.getId());
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
        assertEquals(userId, project.getUserId());
        assertEquals(timestamp, project.getTimestamp());
        assertEquals(gitHubRepoName, project.getGitHubRepoName());
        assertEquals(atlassianProjectId, project.getAtlassianProjectId());
    }
}