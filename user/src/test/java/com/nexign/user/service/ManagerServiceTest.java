package com.nexign.user.service;

import com.nexign.common.model.Role;
import com.nexign.common.model.UserCredentialModel;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.repository.UserRepository;
import com.nexign.user.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void should_save_one_manager() {
        managerService = new ManagerServiceImpl(userRepository, passwordEncoder);

        String password = "testPassword";
        String username = "testUsername";

        UserCredentialModel model = new UserCredentialModel(password, username, Role.MANAGER);
        UserCredential expectedUser = new UserCredential(Role.MANAGER, password, username);

        doReturn(expectedUser).when(userRepository).save(any(UserCredential.class));

        managerService.saveManager(model);

        verify(userRepository, times(1)).save(any(UserCredential.class));
    }
}
