package com.gitcolab.dro.atlassian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Description {
    private Content[] content;
    private String type;
    private int version;
}
