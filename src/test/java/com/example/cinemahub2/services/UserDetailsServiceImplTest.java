package com.example.cinemahub2.services;

import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.repository.user.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testLoadUserByUsername_UserFound_ReturnsUserDetails() {
        // Arrange
        String username = "test@example.com";
        AppUser user = new AppUser();
        user.setUserName(username);
        user.setEmail(username);
        user.setPassword("password123");
        user.setRoles(List.of("USER", "ADMIN"));
        when(userRepo.findByEmail(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("password123", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepo, times(1)).findByEmail(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        // Arrange
        String username = "nonexistent@example.com";
        when(userRepo.findByEmail(username)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username));
        assertEquals("User not found with email: " + username, exception.getMessage());
        verify(userRepo, times(1)).findByEmail(username);
    }
}