package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.LoginResponse;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotAuthorizedException;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotFoundException;
import com.example.cinemahub2.configAndUtility.JwtUtil;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class Login {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(Login.class);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {

        String emailInReq = user.getEmail();
        String passwordInReq = user.getPassword();

        Optional<AppUser> optionalUser = userService.findByEmail(emailInReq);
        AppUser user2 = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with email: " + emailInReq));

        if (!passwordEncoder.matches(passwordInReq, user2.getPassword())) {
            logger.warn("Login failed: Incorrect password for email - {}", emailInReq);
            throw new UserNotAuthorizedException("Incorrect password");
        }

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );


           Optional<AppUser>  optionalAppUser =userService.findByEmail(user.getEmail());
            AppUser appUser2 = optionalAppUser.orElseThrow(() ->
                    new UserNotFoundException("User not found with email: " + user.getEmail()) // Or a custom UserNotFoundException
            );
            String token = jwtUtil.generateToken(appUser2);

            // Update fetched entity
            appUser2.setLoggedIn(true);

            userService.updateUser(appUser2);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("data", new LoginResponse(token));
            return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
        } catch (UserNotFoundException ex) {
            throw new UserNotAuthorizedException("Invalid credentials");
        }
    }
}
