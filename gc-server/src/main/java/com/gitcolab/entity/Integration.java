package com.gitcolab.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Integration {
    private long id;
    private EnumIntegrationType type;
    private String repositoryId;
    private String token;
    private long userId;

    public Integration(EnumIntegrationType type, String token, long userId) {
        this.type = type;
        this.token = token;
        this.userId = userId;
    }

    public Integration(EnumIntegrationType type, String repositoryId, String token, long userId) {
        this.type = type;
        this.repositoryId = repositoryId;
        this.token = token;
        this.userId = userId;
    }
}
