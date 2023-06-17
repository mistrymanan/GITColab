package com.gitcolab.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("FNameLName")
                .email("FName.LName@dal.ca")
                .password("password123")
                .firstName("FName")
                .lastName("LName")
                .roles(new HashSet<>())
                .build();
    }

    @Test
    public void testGetters() {
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("FNameLName", user.getUsername());
        Assertions.assertEquals("FName.LName@dal.ca", user.getEmail());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals("FName", user.getFirstName());
        Assertions.assertEquals("LName", user.getLastName());
        Assertions.assertNotNull(user.getRoles());
    }

    @Test
    public void testConstructor() {
        User newUser = new User("FNameLName", "FName.LName@example.com", "password456", "FName", "LName");

        Assertions.assertEquals("FNameLName", newUser.getUsername());
        Assertions.assertEquals("FName.LName@example.com", newUser.getEmail());
        Assertions.assertEquals("password456", newUser.getPassword());
        Assertions.assertEquals("FName", newUser.getFirstName());
        Assertions.assertEquals("LName", newUser.getLastName());
        Assertions.assertNotNull(newUser.getRoles());
    }

    @Test
    public void testBuilderConstructor() {
        User newUser = User.builder()
                .username("FNameLName")
                .email("FName.LName@example.com")
                .password("password456")
                .firstName("FName")
                .lastName("LName")
                .build();

        Assertions.assertEquals("FNameLName", newUser.getUsername());
        Assertions.assertEquals("FName.LName@example.com", newUser.getEmail());
        Assertions.assertEquals("password456", newUser.getPassword());
        Assertions.assertEquals("FName", newUser.getFirstName());
        Assertions.assertEquals("LName", newUser.getLastName());
        Assertions.assertNull(newUser.getRoles());
    }

    @Test
    public void testSetters() {
        user.setId(2L);
        user.setUsername("FNameLName");
        user.setEmail("FName.LName@example.com");
        user.setPassword("newPassword");
        user.setFirstName("FName");
        user.setLastName("LName");
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        Assertions.assertEquals(2L, user.getId());
        Assertions.assertEquals("FNameLName", user.getUsername());
        Assertions.assertEquals("FName.LName@example.com", user.getEmail());
        Assertions.assertEquals("newPassword", user.getPassword());
        Assertions.assertEquals("FName", user.getFirstName());
        Assertions.assertEquals("LName", user.getLastName());
        Assertions.assertEquals(roles, user.getRoles());
    }
}
