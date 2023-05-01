package com.nexign.brt.service;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountTransaction;
import com.nexign.brt.exception.PaymentLessThanZeroException;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.repository.AccountTransactionRepository;
import com.nexign.brt.service.impl.AccountTransactionServiceImpl;
import com.nexign.common.model.FindByPhoneModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionTest {

    @InjectMocks
    private AccountTransactionServiceImpl accountTransactionService;

    @Mock
    private UserGateway userGateway;

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountTransactionService = new AccountTransactionServiceImpl(
          userGateway, accountTransactionRepository, accountRepository
        );
    }

    @Test
    public void should_add_transaction_returned_transaction_id() throws PaymentLessThanZeroException {
        String phone = "89997776655";
        long userId = 4L;
        double amount = 200;
        long expectedTransactionId = 10L;
        String username = "username";

        FindByPhoneModel model = new FindByPhoneModel(userId, 3L, username);
        Account account = new Account(userId, amount);
        AccountTransaction accountTransaction = new AccountTransaction(account, amount);
        accountTransaction.setId(expectedTransactionId);

        when(userGateway.getUserInfo(phone)).thenReturn(model);
        when(accountRepository.findByUserId(model.getUserId())).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountTransactionRepository.save(any(AccountTransaction.class))).thenReturn(accountTransaction);

        long actualTransactionId = accountTransactionService.addTransaction(phone, amount);

        verify(userGateway, times(1)).getUserInfo(phone);
        verify(accountRepository, times(1)).findByUserId(model.getUserId());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(accountTransactionRepository, times(1)).save(any(AccountTransaction.class));

        assertEquals(expectedTransactionId, actualTransactionId);
    }

}
