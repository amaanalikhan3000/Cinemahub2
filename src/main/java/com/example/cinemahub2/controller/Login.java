package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.LoginResponse;
import com.example.cinemahub2.configAndUtility.JwtUtil;
import com.example.cinemahub2.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private com.example.cinemahub2.services.userService userService;

    private static final Logger logger = LoggerFactory.getLogger(Login.class);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {

        String emailInReq = user.getEmail();
        String passwordInReq = user.getPassword();


        Optional<AppUser> optionalUser = userService.findByEmail(emailInReq);
        AppUser user2 = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + optionalUser));


        if (!(user2.getEmail().equals(emailInReq))) {
            logger.warn("Login failed: Email not registered - {}", emailInReq);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email not registered");
        }


        if (!passwordEncoder.matches(passwordInReq, user2.getPassword())) {
            logger.warn("Login failed: Incorrect password for email - {}", emailInReq);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }




        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );


           Optional<AppUser>  optionalAppUser =userService.findByEmail(user.getEmail());
            AppUser appUser2 = optionalAppUser.orElseThrow(() ->
                    new RuntimeException("User not found with email: " + user.getEmail()) // Or a custom UserNotFoundException
            );
            String token = jwtUtil.generateToken(appUser2);

            // Update fetched entity
            appUser2.setLoggedIn(true);

            userService.updateUser(appUser2);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("data", new LoginResponse(token));
            return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
