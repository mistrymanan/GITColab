package com.gitcolab.dro.atlassian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessibleResource {
    String id;
    String url;
    String name;
    String avatarUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessibleResource that = (AccessibleResource) o;
        return Objects.equals(id, that.id) ;
    }

   }
