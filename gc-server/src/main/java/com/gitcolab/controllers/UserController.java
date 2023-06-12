package com.gitcolab.controllers;

import com.gitcolab.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @GetMapping()
    public ResponseEntity<User> getUserExample(){
        return ResponseEntity.ok(new User(1,"First Name","Last Name"));
    }
}