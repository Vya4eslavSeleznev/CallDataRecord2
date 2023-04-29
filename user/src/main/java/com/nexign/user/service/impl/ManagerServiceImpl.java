package com.nexign.user.service.impl;

import com.nexign.common.model.UserCredentialModel;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.repository.UserRepository;
import com.nexign.user.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveManager(UserCredentialModel userCredential) {
        userRepository.save(
          new UserCredential(
            userCredential.getRole(),
            passwordEncoder.encode(userCredential.getPassword()),
            userCredential.getUsername())
        );
    }
}
