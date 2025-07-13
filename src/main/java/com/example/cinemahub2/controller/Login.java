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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

//    @PostMapping("/createAccount")
//    public ResponseEntity<?> createUser(@RequestBody AppUser user) {
//        userService.saveNewUser(user);
//        return new ResponseEntity<>("Account created ", HttpStatus.CREATED);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
//        String emailInReq = user.getEmail();
//        String passwordInReq = user.getPassword();
//
//        AppUser optionalUser = userService.findByEmail(emailInReq);
//
//        if (!(optionalUser.getEmail().equals(emailInReq))) {
//            logger.warn("Login failed: Email not registered - {}", emailInReq);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Email not registered");
//            }
//
//            //  AppUser userInDb = optionalUser.get);
//
//            if (!passwordEncoder.matches(passwordInReq, optionalUser.getPassword())) {
//                logger.warn("Login failed: Incorrect password for email - {}", emailInReq);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("Incorrect password");
//            }
//
//            // Update fetched entity
//            optionalUser.setLoggedIn(true);
//
//            // optionalUser.updateUser(userInDb);
//            // save the DB-managed object
//            userService.updateUser(optionalUser);
//
//
//            return new ResponseEntity<>("Login successful", HttpStatus.ACCEPTED);

        String emailInReq = user.getEmail();
        String passwordInReq = user.getPassword();

        AppUser optionalUser = userService.findByEmail(emailInReq);

        if (!(optionalUser.getEmail().equals(emailInReq))) {
            logger.warn("Login failed: Email not registered - {}", emailInReq);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email not registered");
        }


        if (!passwordEncoder.matches(passwordInReq, optionalUser.getPassword())) {
            logger.warn("Login failed: Incorrect password for email - {}", emailInReq);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }


//        try {
//            // Authenticate user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
//            );
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        final AppUser appUser = userService.findByEmail(user.getEmail());
//
//        // Generate token
//      //  final String jwt = jwtUtil.generateToken(appUser);
//
//        // Return the token
//      //  return ResponseEntity.ok(jwt);
//        return new ResponseEntity<>("Login successful", HttpStatus.ACCEPTED);


        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

        //    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AppUser appUser = userService.findByEmail(user.getEmail());
            String token = jwtUtil.generateToken(appUser);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
