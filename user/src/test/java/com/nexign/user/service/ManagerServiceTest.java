package com.nexign.user.service;

import com.nexign.common.model.Role;
import com.nexign.common.model.UserCredentialModel;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.repository.UserRepository;
import com.nexign.user.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ManagerServiceImpl managerService;

    @Test
    public void should_save_one_manager() {
        String password = "testPassword";
        String username = "testUsername";

        UserCredentialModel model = new UserCredentialModel(password, username, Role.MANAGER);
        UserCredential expectedUser = new UserCredential(Role.MANAGER, password, username);

        doReturn(expectedUser).when(userRepository).save(expectedUser);

        UserCredential actualUser = userRepository.save(expectedUser);
        managerService.saveManager(model);

        verify(userRepository, times(1)).save(expectedUser);
        verify(managerService, times(1)).saveManager(model);

        assertEquals(expectedUser, actualUser);
    }
}
