package com.gitcolab.dao;

import com.gitcolab.entity.Integration;

import java.util.Optional;

public interface GithubDAO extends DAO<Integration> {

    public Optional<Integration> getByEmail(String email);

    public Optional<Integration> getByUsername(String username);
}
