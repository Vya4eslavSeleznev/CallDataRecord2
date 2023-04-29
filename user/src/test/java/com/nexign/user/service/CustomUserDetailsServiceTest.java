package com.nexign.user.service;

import com.nexign.common.model.Role;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.InvalidUserNameOrPasswordException;
import com.nexign.user.repository.UserRepository;
import com.nexign.user.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String username;
    private String password;
    private UserCredential expectedUC;
    private Optional<UserCredential> optionalUC;

    @BeforeEach
    public void init() {
        customUserDetailsService = new CustomUserDetailsService(userRepository, passwordEncoder);
        username = "username";
        password = "pwd";
        expectedUC = new UserCredential(Role.USER, password, username);
        optionalUC = Optional.of(expectedUC);
    }

    @Test
    public void should_load_user_by_username_returned_user_details() {
        when(userRepository.findUserCredentialByUsername(username)).thenReturn(optionalUC);

        UserDetails actualUD = customUserDetailsService.loadUserByUsername(username);

        verify(userRepository, times(1)).findUserCredentialByUsername(username);

        assertEquals(expectedUC, actualUD);
    }

    @Test
    public void should_not_load_user_by_username_exception() {
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }

    @Test
    public void should_get_authenticated_user_returned_user_credential() throws InvalidUserNameOrPasswordException {
        when(userRepository.findUserCredentialByUsername(username)).thenReturn(optionalUC);
        when(passwordEncoder.matches(password, password)).thenReturn(true);

        UserCredential actualUC = customUserDetailsService.getAuthenticatedUser(username, password);

        verify(userRepository, times(1)).findUserCredentialByUsername(username);

        assertEquals(expectedUC, actualUC);
    }

    @Test
    public void should_get_empty_user_exception() {
        assertThrows(InvalidUserNameOrPasswordException.class, () ->
          customUserDetailsService.getAuthenticatedUser(username, password));
    }

    @Test
    public void should_get_incorrect_password_exception() {
        when(userRepository.findUserCredentialByUsername(username)).thenReturn(optionalUC);
        when(passwordEncoder.matches(password, password)).thenReturn(false);
        assertThrows(InvalidUserNameOrPasswordException.class, () ->
          customUserDetailsService.getAuthenticatedUser(username, password));
    }
}
