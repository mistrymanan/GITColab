package com.gitcolab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    /*Added a bunch of fields but need some clarity as to where i would use a DTO.
    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String otp;
    private String otpExpiry;
    private String organization;
    private String location;
    private String description;
    private String linkedin;
    private String github;
    private String resume;
    */


}
