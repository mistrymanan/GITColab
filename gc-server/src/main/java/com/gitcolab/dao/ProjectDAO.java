package com.gitcolab.dao;

import com.gitcolab.entity.Project;

import java.util.Optional;

public interface ProjectDAO extends DAO {
    Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName, String repositoryOwner);
}
