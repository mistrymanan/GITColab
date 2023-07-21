package com.gitcolab.utilities;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setDescription(resultSet.getString("description"));
        project.setUserId(resultSet.getInt("userId"));
        project.setTimestamp(resultSet.getTimestamp("timestamp").toInstant());
        project.setGitHubRepoName(resultSet.getString("gitHubRepoName"));
        project.setAtlassianProjectId(resultSet.getInt("atlassianProjectId"));
        return project;
    }
}
