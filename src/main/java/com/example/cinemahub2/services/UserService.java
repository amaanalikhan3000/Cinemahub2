package com.example.cinemahub2.services;

import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cinemahub2.repository.user.UserRepo;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AppUser saveNewUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
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
