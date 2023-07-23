package com.gitcolab.repositories;

import com.gitcolab.dao.IntegrationDAO;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntegrationRepositoryImplementation implements IntegrationRepository {

    @Autowired
    IntegrationDAO integrationDAO;

    @Override
    public int save(Integration integration) {
        return integrationDAO.save(integration);
    }

    @Override
    public int update(Integration integration) {
        return integrationDAO.update(integration);
    }

    @Override
    public Optional<Integration> getByEmail(String email, EnumIntegrationType type) {
        return integrationDAO.getByEmail(email,type);
    }

    @Override
    public Optional<Integration> getByUsername(String username) {
        return integrationDAO.getByUsername(username);
    }
}
