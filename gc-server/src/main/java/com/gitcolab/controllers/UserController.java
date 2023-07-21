package com.gitcolab.controllers;


import com.gitcolab.dao.UserDAO;
import com.gitcolab.dto.*;

import com.gitcolab.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDao;


    @GetMapping()
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok(userDao.getUserByUserName("Uchenna"));

    }

    @PostMapping("/userProfile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        return userService.updateUserProfile(updateUserProfileRequest);
    }



    /*
    @GetMapping
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok(new JDBCUserDAO().getUserByUserName("Uchenna"));
    }
    */
}