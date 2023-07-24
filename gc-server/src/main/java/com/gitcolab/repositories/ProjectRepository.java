package com.gitcolab.repositories;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository {
    int save(Project project);
    void delete(RefreshToken t);
}
