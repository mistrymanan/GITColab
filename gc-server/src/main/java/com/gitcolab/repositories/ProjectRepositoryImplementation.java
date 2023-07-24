package com.gitcolab.repositories;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public class ProjectRepositoryImplementation implements ProjectRepository {
    @Override
    public int save(Project project) {
        return 0;
    }

    @Override
    public void delete(RefreshToken t) {

    }
}
