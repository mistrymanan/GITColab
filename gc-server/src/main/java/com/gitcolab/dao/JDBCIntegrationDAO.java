package com.gitcolab.dao;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import com.gitcolab.utilities.IntegrationRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JDBCIntegrationDAO implements IntegrationDAO {
    Logger logger= LoggerFactory.getLogger(JDBCIntegrationDAO.class);
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional get(long id) {
        Integration integration = jdbcTemplate.queryForObject("select * from Integration i where i.id=?",new Object[]{id}, new IntegrationRowMapper());
        return Optional.of(integration);
    }

    @Override
    public int save(Integration integration) {
        AbstractSqlParameterSource namedParameters = new BeanPropertySqlParameterSource(integration);
        namedParameters.registerTypeName("typeString", integration.getType().toString());
        return namedParameterJdbcTemplate
                .update("INSERT INTO Integration(`type`,`repositoryId`,`token`,`userId`) values(:typeString,:repositoryId,:token,:userId)"
                        ,namedParameters);
    }

    @Override
    public int update(Integration integration) {
        AbstractSqlParameterSource namedParameters = new BeanPropertySqlParameterSource(integration);
        namedParameters.registerTypeName("typeString", integration.getType().toString());
        return namedParameterJdbcTemplate
                .update("UPDATE Integration SET `repositoryId` = :repositoryId,`token` = :token WHERE `userId` = :userId and `type` = :typeString"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<Integration> getByEmail(String email, EnumIntegrationType integrationType) {
        Integration integration = new Integration();
        try {
            integration = jdbcTemplate
                    .queryForObject(
                            "select * from Integration i where i.userId = (Select id from User u where u.email = ?) AND i.type=?"
                    ,new Object[]{email,integrationType.toString()}
                    , new IntegrationRowMapper());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(integration);
    }

    @Override
    public Optional<Integration> getByUsername(String username) {
        return null;
    }
}
