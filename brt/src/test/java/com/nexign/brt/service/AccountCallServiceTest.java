package com.nexign.brt.service;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountCall;
import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.impl.AccountCallServiceImpl;
import com.nexign.common.model.CallCostCalculatedEvent;
import com.nexign.common.model.CallType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountCallServiceTest {

    @InjectMocks
    private AccountCallServiceImpl accountCallService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountCallRepository accountCallRepository;

    @BeforeEach
    public void init() {
        accountCallService = new AccountCallServiceImpl(accountRepository, accountCallRepository);
    }

    @Test
    public void should_add_call() throws BalanceLessThanZeroException, AccountNotFoundException {
        CallType callType = CallType.INPUT;
        Date startDate = new Date();
        Date endDate = new Date();
        Duration duration = Duration.ofMillis(100);
        double cost = 100;
        long accountId = 10L;

        CallCostCalculatedEvent callCostCalculatedEvent = new CallCostCalculatedEvent(
          accountId, callType, cost, startDate, endDate, duration
        );

        Account account = new Account(7L, 100);
        Optional<Account> accountOpt = Optional.of(account);
        account.setId(accountId);

        AccountCall accountCall = new AccountCall(account, callType, startDate, endDate, duration, cost);

        doReturn(accountOpt).when(accountRepository).findById(account.getId());
        doReturn(accountCall).when(accountCallRepository).save(any(AccountCall.class));
        doReturn(account).when(accountRepository).save(any(Account.class));

        accountCallService.addCall(callCostCalculatedEvent);

        verify(accountRepository, times(1)).findById(account.getId());
        verify(accountCallRepository, times(1)).save(any(AccountCall.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
