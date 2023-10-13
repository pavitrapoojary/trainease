package com.trainease.service;

import com.trainease.repository.UserRepository;
import com.trainease.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthService userAuthService;

    @Test
    public void testLoadUserByUsername_UserFound() {
        String emailId = "test@example.com";
        String hashedPassword = "hashedPassword";
        com.trainease.entity.User userEntity = com.trainease.entity.User.builder()
                .emailId(emailId)
                .password(hashedPassword).build();

        when(userRepository.findById(emailId)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userAuthService.loadUserByUsername(emailId);

        assertNotNull(userDetails);
        assertEquals(emailId, userDetails.getUsername());
        assertEquals(hashedPassword, userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String nonExistentUserEmailId = "nonexistent";
        when(userRepository.findById(nonExistentUserEmailId)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userAuthService.loadUserByUsername(nonExistentUserEmailId);
        });

        verify(userRepository).findById(nonExistentUserEmailId);
        assertEquals("User not found with username : " + nonExistentUserEmailId, exception.getMessage());
    }

}