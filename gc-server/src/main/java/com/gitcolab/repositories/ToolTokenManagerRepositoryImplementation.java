package com.gitcolab.repositories;

import com.gitcolab.dao.ToolTokenManagerDAO;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToolTokenManagerRepositoryImplementation implements ToolTokenManagerRepository {

    @Autowired
    ToolTokenManagerDAO integrationDAO;

    @Override
    public int save(ToolTokenManager integration) {
        return integrationDAO.save(integration);
    }

    @Override
    public int update(ToolTokenManager integration) {
        return integrationDAO.update(integration);
    }

    @Override
    public Optional<ToolTokenManager> getByEmail(String email, EnumIntegrationType type) {
        return integrationDAO.getByEmail(email,type);
    }

    @Override
    public Optional<ToolTokenManager> getByUsername(String username) {
        return integrationDAO.getByUsername(username);
    }
}
