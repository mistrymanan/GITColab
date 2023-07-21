package com.gitcolab.repositories;

import com.gitcolab.dao.GithubDAO;
import com.gitcolab.entity.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GithubRepositoryImplementation implements GithubRepository {

    @Autowired
    GithubDAO githubDAO;

    @Override
    public int save(Integration github) {
        return githubDAO.save(github);
    }

    @Override
    public int update(Integration github) {
        return githubDAO.update(github);
    }

    @Override
    public Optional<Integration> getByEmail(String email) {
        return githubDAO.getByEmail(email);
    }

    @Override
    public Optional<Integration> getByUsername(String username) {
        return githubDAO.getByUsername(username);
    }
}
