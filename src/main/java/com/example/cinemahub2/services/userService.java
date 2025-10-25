package com.example.cinemahub2.services;

import com.example.cinemahub2.DTO.CreateUserDTO;
import com.example.cinemahub2.Exception.ExceptionsHandler.BookingConflictException;
import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cinemahub2.repository.user.userRepo;
import java.util.Arrays;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AppUser saveNewUser(CreateUserDTO userDTO) {

        if(userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new BookingConflictException("The email address " + userDTO.getEmail() + " is already registered.");
        }
        AppUser user = new AppUser();

        user.setUserName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("USER"));
        user.setLoggedIn(false);
        userRepo.save(user);
        return user;
    }

    public AppUser updateUser(AppUser user) {
        userRepo.save(user);
        return user;
    }


    public Optional<AppUser> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
