package com.example.cinemahub2.controller;

import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class logOut {
    @Autowired
    private com.example.cinemahub2.services.userService userService;

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody AppUser user) {
        String emailInReq = user.getEmail();
        AppUser optionalUser = userService.findByEmail(emailInReq);

//        AppUser userInDb = optionalUser.get();

        // Update fetched entity
        optionalUser.setLoggedIn(false);
        userService.updateUser(optionalUser); // save the DB-managed object

        return new ResponseEntity<>("Successfully Logged out", HttpStatus.ACCEPTED);
    }
}
