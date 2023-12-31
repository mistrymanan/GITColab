package com.gitcolab.services;

import com.gitcolab.dro.atlassian.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AtlassianServiceClientTest {

    @Mock
    AtlassianServiceClient atlassianServiceClient;

    @Test
    public void testGetAccessibleResources_Success() {

        String bearerToken = "SAMPLE_BEARER_TOKEN";


        List<AccessibleResource> sampleResources = new ArrayList<>();
        sampleResources.add(new AccessibleResource("1", "https://example.com/resource/1", "Resource 1", "https://example.com/avatar/1"));
        sampleResources.add(new AccessibleResource("2", "https://example.com/resource/2", "Resource 2", "https://example.com/avatar/2"));


        when(atlassianServiceClient.getAccessibleResources(anyString())).thenReturn(sampleResources);


        List<AccessibleResource> accessibleResources = atlassianServiceClient.getAccessibleResources(bearerToken);


        assertEquals(sampleResources, accessibleResources);
    }

    @Test
    public void testGetAllProjects() {

        String cloudId = "SAMPLE_CLOUD_ID";
        String bearerToken = "SAMPLE_BEARER_TOKEN";


        List<ProjectResponse> sampleProjects = new ArrayList<>();
        sampleProjects.add(new ProjectResponse("http://example.com/projects/1", "P1", "SAMPLE_KEY_1", "Project 1"));
        sampleProjects.add(new ProjectResponse("http://example.com/projects/2", "P2", "SAMPLE_KEY_2", "Project 2"));


        when(atlassianServiceClient.getAllProjects(anyString(), anyString())).thenReturn(sampleProjects);


        List<ProjectResponse> allProjects = atlassianServiceClient.getAllProjects(cloudId, bearerToken);


        assertEquals(sampleProjects, allProjects);
    }

    @Test
    public void testGetProject() {

        String cloudId = "SAMPLE_CLOUD_ID";
        String bearerToken = "SAMPLE_BEARER_TOKEN";
        String projectId = "ProjectId";

        ProjectResponse response = new ProjectResponse("http://example.com/projects/1", "P1", "SAMPLE_KEY_1", "Project 1");


        when(atlassianServiceClient.getProject(anyString(), anyString(), anyString())).thenReturn(response);


        ProjectResponse project = atlassianServiceClient.getProject(cloudId, projectId, bearerToken);


        assertEquals(response, project);
    }

    @Test
    void createProject() {

        String cloudId = "SAMPLE_CLOUD_ID";
        String bearerToken = "SAMPLE_BEARER_TOKEN";
        ProjectCreateRequest createRequest = new ProjectCreateRequest("Sample description", "SAMPLE_KEY", "L123", "Sample Project");


        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        when(atlassianServiceClient.createProject(anyString(), anyString(), any())).thenReturn(response);


        ResponseEntity<Void> createProjectResponse = atlassianServiceClient.createProject(cloudId, bearerToken, createRequest);


        assertEquals(HttpStatus.CREATED, createProjectResponse.getStatusCode());
        assertEquals(response, createProjectResponse);

    }

    @Test
    void getUserDetails() {

        String cloudId = "SAMPLE_CLOUD_ID";
        String bearerToken = "SAMPLE_BEARER_TOKEN";


        MySelfResponse sampleUsers = new MySelfResponse("http://example.com/users/1", "1234", "U1", "user1@example.com", "User 1", true);


        when(atlassianServiceClient.getUserDetails(anyString(), anyString())).thenReturn(sampleUsers);


        MySelfResponse userDetails = atlassianServiceClient.getUserDetails(cloudId, bearerToken);


        assertEquals(sampleUsers, userDetails);
    }

    @Test
    void createIssueRequest() {

        String cloudId = "SAMPLE_CLOUD_ID";
        String bearerToken = "SAMPLE_BEARER_TOKEN";
        CreateIssueRequest createIssueRequest = new CreateIssueRequest(/* create your request object here */);


        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        when(atlassianServiceClient.createIssueRequest(anyString(), anyString(), any())).thenReturn(response);


        ResponseEntity<Void> createIssueResponse = atlassianServiceClient.createIssueRequest(cloudId, bearerToken, createIssueRequest);


        assertEquals(HttpStatus.CREATED, createIssueResponse.getStatusCode());

    }
}