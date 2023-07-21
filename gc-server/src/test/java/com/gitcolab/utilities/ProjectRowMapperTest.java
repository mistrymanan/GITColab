package com.gitcolab.utilities;

import com.gitcolab.entity.Project;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {

        ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        int id = 1;
        String name = "Test Project";
        String description = "This is a test project.";
        int userId = 42;
        Instant timestamp = Instant.now();
        String gitHubRepoName = "test-repo";
        int atlassianProjectId = 100;

        Mockito.when(resultSetMock.getInt("id")).thenReturn(id);
        Mockito.when(resultSetMock.getString("name")).thenReturn(name);
        Mockito.when(resultSetMock.getString("description")).thenReturn(description);
        Mockito.when(resultSetMock.getInt("userId")).thenReturn(userId);
        Mockito.when(resultSetMock.getTimestamp("timestamp")).thenReturn(Timestamp.from(timestamp));
        Mockito.when(resultSetMock.getString("gitHubRepoName")).thenReturn(gitHubRepoName);
        Mockito.when(resultSetMock.getInt("atlassianProjectId")).thenReturn(atlassianProjectId);

        ProjectRowMapper projectRowMapper = new ProjectRowMapper();
        Project mappedProject = projectRowMapper.mapRow(resultSetMock, 1);


        assertEquals(id, mappedProject.getId());
        assertEquals(name, mappedProject.getName());
        assertEquals(description, mappedProject.getDescription());
        assertEquals(userId, mappedProject.getUserId());
        assertEquals(timestamp, mappedProject.getTimestamp());
        assertEquals(gitHubRepoName, mappedProject.getGitHubRepoName());
        assertEquals(atlassianProjectId, mappedProject.getAtlassianProjectId());
    }
}