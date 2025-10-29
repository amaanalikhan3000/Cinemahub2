package com.example.cinemahub2.controller;

import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotFoundException;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class logOut {
    @Autowired
    private UserService userService;

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody AppUser user) {
        String emailInReq = user.getEmail();
        Optional<AppUser> optionalUser = userService.findByEmail(emailInReq);
        AppUser appUser = optionalUser.orElseThrow(() ->
                new UserNotFoundException("User not found with email: " + user.getEmail())
        );


        // Update fetched entity
        appUser.setLoggedIn(false);
        userService.updateUser(appUser); // save the DB-managed object

        return new ResponseEntity<>("Successfully Logged out", HttpStatus.ACCEPTED);
    }
}
