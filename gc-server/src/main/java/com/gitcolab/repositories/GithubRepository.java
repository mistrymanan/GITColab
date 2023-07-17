package com.gitcolab.repositories;


import com.gitcolab.entity.Integration;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubRepository {
    int save(Integration github);
    int update(Integration github);

    Optional<Integration> getByEmail(String email);

    public Optional<Integration> getByUsername(String username);
}
