package com.gitcolab.dao;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;

import java.util.Optional;

public interface IntegrationDAO extends DAO<Integration> {

    public Optional<Integration> getByEmail(String email, EnumIntegrationType type);

    public Optional<Integration> getByUsername(String username);
}
