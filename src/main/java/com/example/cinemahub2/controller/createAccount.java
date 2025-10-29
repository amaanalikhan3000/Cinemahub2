package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.CreateUserDTO;
import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemahub2.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/create")
public class createAccount {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO user) {

        userService.saveNewUser(user);
        return new ResponseEntity<>("Account created ", HttpStatus.CREATED);
    }
}
