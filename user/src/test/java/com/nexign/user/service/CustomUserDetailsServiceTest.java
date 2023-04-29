package com.nexign.user.service;

import com.nexign.common.model.Role;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.repository.UserRepository;
import com.nexign.user.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        customUserDetailsService = new CustomUserDetailsService(userRepository, passwordEncoder);
    }

    @Test
    public void load_user_by_username() {
        String username = "test";

        UserCredential expectedUC = new UserCredential(Role.USER, "pwd", username);

        Optional<UserCredential> optionalUC = Optional.of(
          expectedUC
        );

        when(userRepository.findUserCredentialByUsername(username)).thenReturn(optionalUC);

        UserDetails actualUD = customUserDetailsService.loadUserByUsername(username);

        verify(userRepository, times(1)).findUserCredentialByUsername(username);

        assertEquals(expectedUC, actualUD);
    }
}
