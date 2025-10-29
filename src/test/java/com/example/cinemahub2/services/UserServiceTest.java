package com.example.cinemahub2.services;

import com.example.cinemahub2.DTO.CreateUserDTO;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.repository.user.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserService userService;


    @Test
    void testSaveNewUser() {
        // arrange

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setPassword("password");


        // act
        AppUser user = userService.saveNewUser(createUserDTO);

        // assert
        assertThat(user.getPassword()).isNotNull();
        verify(userRepo, times(1)).save(any(AppUser.class));
    }


    @Test
    void testUpdateUser() {
        // arrange
        AppUser input = new AppUser();
        input.setPassword("password");

        // act
        AppUser user = userService.updateUser(input);

        // assert
        assertThat(user.getPassword()).isNotNull();
        verify(userRepo, times(1)).save(any(AppUser.class));
    }

    @Test
    void testFindByEmail() {
        // arrange
        final String email = "a@a.com";
        AppUser appUser = new AppUser();
        appUser.setUserName(email);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(appUser));

        // act
        Optional<AppUser> appUserOpt = userService.findByEmail(email);

        // assert
        assertThat(appUserOpt.isPresent()).isTrue();
    }
}