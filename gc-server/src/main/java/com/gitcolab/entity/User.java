package com.gitcolab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private long id;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new HashSet<>();
    private String otp;
    private String otpExpiry;

    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String otp, String otpExpiry) {
        this.email = email;
        this.otp = otp;
        this.otpExpiry = otpExpiry;
    }

    public User(String username, String email, String password, String uFname, String uLname, String abc123, String s) {
    }
}
