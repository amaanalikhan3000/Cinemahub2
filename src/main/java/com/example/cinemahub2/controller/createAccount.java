package com.example.cinemahub2.controller;

import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create")
public class createAccount {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private com.example.cinemahub2.services.userService userService;


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody AppUser user) {
        userService.saveNewUser(user);
        return new ResponseEntity<>("Account created ", HttpStatus.CREATED);
    }
}
