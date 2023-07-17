package com.gitcolab.utilities;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
public class IntegrationRowMapperTest {

    @Test
    void testMapRow() throws SQLException {
        // Arrange
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getString("type")).thenReturn("GITHUB");
        Mockito.when(resultSet.getString("repositoryId")).thenReturn("repository123");
        Mockito.when(resultSet.getString("token")).thenReturn("token123");
        Mockito.when(resultSet.getInt("userId")).thenReturn(10);

        IntegrationRowMapper integrationMapper = new IntegrationRowMapper();

        Integration integration = integrationMapper.mapRow(resultSet, 0);

        Assertions.assertEquals(1, integration.getId());
        Assertions.assertEquals(EnumIntegrationType.GITHUB, integration.getType());
        Assertions.assertEquals("repository123", integration.getRepositoryId());
        Assertions.assertEquals("token123", integration.getToken());
        Assertions.assertEquals(10, integration.getUserId());
    }
}
