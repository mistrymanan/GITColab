package com.gitcolab.controllers;

import com.gitcolab.dto.UserDTO;
import com.gitcolab.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @GetMapping()
    public ResponseEntity<UserDTO> getUserExample(){

        return ResponseEntity.ok(new UserDTO("username"));
    }
}