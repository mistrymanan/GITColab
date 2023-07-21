package com.gitcolab.dro.atlassian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySelfResponse {
    private String self;
    private String accountId;
    private String accountType;
    private String emailAddress;
    private String displayName;
    private boolean active;
}
