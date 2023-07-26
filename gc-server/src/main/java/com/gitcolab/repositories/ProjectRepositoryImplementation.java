package com.gitcolab.repositories;

import com.gitcolab.dao.ProjectDAO;
import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectRepositoryImplementation implements ProjectRepository {

    @Autowired
    ProjectDAO projectDAO;
    @Override
    public int save(Project project) {
        projectDAO.save(project);
        return 0;
    }

    @Override
    public void delete(RefreshToken t) {

    }

    @Override
    public boolean isJiraBoardExist(String jiraBoardName, String userId) {
        return false;
    }

    @Override
    public Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName, String repositoryOwner) {
        return projectDAO.getProjectByRepositoryNameAndOwner(repositoryName,repositoryOwner);
//        return Optional.empty();
    }
//    Optional<Project> getProjectByRepositoryNameAndOwner();
}
