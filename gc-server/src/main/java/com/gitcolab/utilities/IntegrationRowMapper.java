package com.gitcolab.utilities;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import com.gitcolab.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegrationRowMapper implements RowMapper<Integration> {
    @Override
    public Integration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integration integration = Integration.builder()
                .id(rs.getInt("id"))
                .type(EnumIntegrationType.valueOf(rs.getString("type")))
                .repositoryId(rs.getString("repositoryId"))
                .token(rs.getString("token"))
                .userId(rs.getInt("userId"))
                .build();

        return integration;
    }
}