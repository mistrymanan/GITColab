package com.gitcolab.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor

public class UpdateUserProfileRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String organization;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

    @NotBlank
    private String linkedin;

    @NotBlank
    private String github;

    //per discussion with Baseer, resume will just be a link to a google drive.
    @NotBlank
    private String resume;
}
