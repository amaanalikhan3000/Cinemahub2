package com.example.cinemahub2.controller;

import com.example.cinemahub2.utils.ResponseUtil;
import com.example.cinemahub2.utils.ValidationUtil;
//import com.example.cinemahub2.model.AppUser;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.services.userService;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotFoundException;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotAuthorizedException;
import com.example.cinemahub2.configAndUtility.JwtUtil;
//import com.example.cinemahub2.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

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

        // Validate input
        if (ValidationUtil.isNullOrEmpty(user.getEmail()) || ValidationUtil.isNullOrEmpty(user.getPassword())) {
            logger.warning("Login attempt with empty email or password");
            return ResponseUtil.error("Email and password must not be empty", HttpStatus.BAD_REQUEST);
        }

        String emailInReq = user.getEmail();
        String passwordInReq = user.getPassword();

        logger.info("Login attempt for email: " + emailInReq);

        Optional<AppUser> optionalUser = userService.findByEmail(emailInReq);
        AppUser userEntity = optionalUser.orElseThrow(() -> {
            logger.warning("Login failed: User not found for email - " + emailInReq);
            return new UserNotFoundException("User not found with email: " + emailInReq);
        });

        if (!passwordEncoder.matches(passwordInReq, userEntity.getPassword())) {
            logger.warning("Login failed: Incorrect password for email - " + emailInReq);
            throw new UserNotAuthorizedException("Incorrect password");
        }

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            // Reload user after authentication
            AppUser appUser = userService.findByEmail(user.getEmail())
                    .orElseThrow(() -> {
                        logger.warning("Login failed: User not found after authentication - " + user.getEmail());
                        return new UserNotFoundException("User not found with email: " + user.getEmail());
                    });

            String token = jwtUtil.generateToken(appUser);

            // Update login status
            appUser.setLoggedIn(true);
            userService.updateUser(appUser);

            logger.info("Login successful for email: " + user.getEmail());

            // Return standardized success response
            return ResponseUtil.success("Login successful", Map.of("token", token));

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Login failed due to exception", ex);
            throw new UserNotAuthorizedException("Invalid credentials");
        }
    }
}
