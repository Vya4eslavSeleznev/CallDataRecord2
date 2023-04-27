package com.nexign.user.service.impl;

import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.InvalidUserNameOrPasswordException;
import com.nexign.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserCredentialByUsername(username).orElseThrow(
          () -> new UsernameNotFoundException("Username not found"));
    }

    public UserCredential getAuthenticatedUser(String username, String password)
      throws InvalidUserNameOrPasswordException {
        Optional<UserCredential> user = userRepository.findUserCredentialByUsername(username);

        if (user.isEmpty()) {
            throw new InvalidUserNameOrPasswordException();
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidUserNameOrPasswordException();
        }

        return user.get();
    }
}
