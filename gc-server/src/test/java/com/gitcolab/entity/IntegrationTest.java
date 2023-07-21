package com.gitcolab.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class IntegrationTest {
    private Integration integration;

    @BeforeEach
    public void setUp() {
        integration = Integration.builder()
                .id(1L)
                .type(EnumIntegrationType.GITHUB)
                .repositoryId("repoId")
                .token("token")
                .userId(3)
                .build();
    }

    @Test
    public void testIntegrationConstructorWithAllArgs() {
        Integration integration1 = new Integration(EnumIntegrationType.GITHUB, "repoId", "token", 1);

        Assertions.assertEquals(EnumIntegrationType.GITHUB, integration1.getType());
        Assertions.assertEquals("repoId", integration1.getRepositoryId());
        Assertions.assertEquals("token", integration1.getToken());
        Assertions.assertEquals(1, integration1.getUserId());
    }

    @Test
    public void testIntegrationConstructorWithToken() {
        EnumIntegrationType type = EnumIntegrationType.GITHUB;
        String token = "abc123";
        long userId = 12345;

        Integration integration = new Integration(type, token, userId);

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getUserId(), userId);
    }

    @Test
    public void testIntegrationConstructorWithRepositoryId() {
        EnumIntegrationType type = EnumIntegrationType.ATLASSIAN;
        String repositoryId = "repo123";
        String token = "xyz789";
        long userId = 67890;

        Integration integration = new Integration(type, repositoryId, token, userId);

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getRepositoryId(), repositoryId);
        Assertions.assertEquals(integration.getUserId(), userId);
    }

    @Test
    public void testIntegrationBuilder() {
        EnumIntegrationType type = EnumIntegrationType.GITHUB;
        String repositoryId = "repo123";
        String token = "xyz789";
        long userId = 67890;

        Integration integration = Integration.builder()
                .type(type)
                .repositoryId(repositoryId)
                .token(token)
                .userId(userId)
                .build();

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getRepositoryId(), repositoryId);
        Assertions.assertEquals(integration.getUserId(), userId);
    }
}
