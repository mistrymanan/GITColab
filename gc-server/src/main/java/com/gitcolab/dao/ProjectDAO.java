package com.gitcolab.dao;

import com.gitcolab.entity.Project;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectDAO extends DAO {
    List<Map<String, Object>> getAllProjects(Long id);
    List<Map<String, Object>> getAllContributors(int id);
    int addContributor(int userId, int projectId);
    Optional<Project> getProjectByRepositoryName(String repositoryName);
}
