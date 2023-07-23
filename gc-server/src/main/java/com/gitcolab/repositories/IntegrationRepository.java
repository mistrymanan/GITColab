package com.gitcolab.repositories;


import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegrationRepository {
    int save(Integration integration);
    int update(Integration integration);

    Optional<Integration> getByEmail(String email, EnumIntegrationType type);

    public Optional<Integration> getByUsername(String username);
}
