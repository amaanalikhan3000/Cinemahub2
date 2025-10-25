package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.LoginResponse;
import com.example.cinemahub2.model.AppUser;
import com.example.cinemahub2.services.userService;
import com.example.cinemahub2.util.JwtUtil;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotAuthorizedException;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class Login {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private userService userService;

    private static final Logger logger = Logger.getLogger(Login.class.getName());

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {

        String emailInReq = user.getEmail();
        String passwordInReq = user.getPassword();

        logger.info("Login attempt for email: " + emailInReq);

        // Check if user exists
        Optional<AppUser> optionalUser = userService.findByEmail(emailInReq);
        AppUser existingUser = optionalUser.orElseThrow(() -> {
            logger.warning("Login failed: User not found for email - " + emailInReq);
            return new UserNotFoundException("User not found with email: " + emailInReq);
        });

        // Verify password
        if (!passwordEncoder.matches(passwordInReq, existingUser.getPassword())) {
            logger.warning("Login failed: Incorrect password for email - " + emailInReq);
            throw new UserNotAuthorizedException("Incorrect password");
        }

        try {
            // Authenticate using Spring Security
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(emailInReq, passwordInReq)
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(existingUser);

            // Update user login status
            existingUser.setLoggedIn(true);
            userService.updateUser(existingUser);

            logger.info("Login successful for email: " + emailInReq);

            // Prepare response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("data", new LoginResponse(token));

            return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);

        } catch (UserNotFoundException ex) {
            logger.log(Level.SEVERE, "Login failed due to exception", ex);
            throw new UserNotAuthorizedException("Invalid credentials");
        }
    }
}
