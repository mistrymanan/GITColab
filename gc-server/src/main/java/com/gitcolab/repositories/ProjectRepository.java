package com.gitcolab.repositories;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository {
    int save(Project project);
    void delete(RefreshToken t);
    boolean isJiraBoardExist(String jiraBoardName,String userId);

    Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName,String repositoryOwner);
}
