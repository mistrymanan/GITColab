package com.gitcolab.repositories;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;

public interface ProjectRepository {
    int save(Project project);
    void delete(RefreshToken t);
}
