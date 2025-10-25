package com.example.cinemahub2.services;

import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.repository.user.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepo userRepo;

    @Test
    void testLoadByUserName() {
        // arrange
        final String email = "a@a.com";
        final String password = "password";
        final String username = "username";
        final AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(password);
        appUser.setUserName(username);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(appUser));

        // act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // assert
        assertThat(userDetails.getUsername()).isEqualTo(email);
    }


    @Test
    void testLoadByUserName_notFound() {
        // arrange
        final String email = "a@a.com";
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        // act + assert
        UsernameNotFoundException userNotFoundException = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email));
        assertThat(userNotFoundException.getMessage()).contains("User not found with email:");
    }
}