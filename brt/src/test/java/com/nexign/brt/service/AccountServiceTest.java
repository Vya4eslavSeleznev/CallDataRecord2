package com.nexign.brt.service;

import com.nexign.brt.entity.Account;
import com.nexign.brt.model.UserBalanceInterface;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.impl.AccountServiceImpl;
import com.nexign.common.model.CreateAccountRequestModel;
import com.nexign.common.model.UserBalanceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private long userId;
    private double balance;

    @BeforeEach
    public void init() {
        accountService = new AccountServiceImpl(accountRepository);
        userId = 7L;
        balance = 100;
    }

    @Test
    public void should_create_account() {
        Account account = new Account(userId, balance);
        CreateAccountRequestModel model = new CreateAccountRequestModel(userId, balance);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.createAccount(model);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void should_get_accounts_returned_list_of_user_balances() {
        List<UserBalanceInterface> accountList = List.of(new UserBalanceInterface() {
            @Override
            public long getUserId() {
                return userId;
            }

            @Override
            public double getBalance() {
                return balance;
            }
        });

        List<UserBalanceModel> expectedList = accountList
          .stream()
          .map(elem -> new UserBalanceModel(
            elem.getUserId(),
            elem.getBalance()
          ))
          .collect(Collectors.toList());

        when(accountRepository.findAllBy()).thenReturn(accountList);

        List<UserBalanceModel> actualList = accountService.getAccounts();

        verify(accountRepository, times(1)).findAllBy();

        assertEquals(expectedList.get(0).getUserId(), actualList.get(0).getUserId());
        assertEquals(expectedList.get(0).getBalance(), actualList.get(0).getBalance());
    }
}
