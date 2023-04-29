package com.nexign.brt.service;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountCall;
import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.impl.AccountCallServiceImpl;
import com.nexign.common.model.AccountCallResponseModel;
import com.nexign.common.model.CallCostCalculatedEvent;
import com.nexign.common.model.CallType;
import com.nexign.common.model.UserCallsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountCallServiceTest {

    @InjectMocks
    private AccountCallServiceImpl accountCallService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountCallRepository accountCallRepository;

    private Account account;
    private CallCostCalculatedEvent callCostCalculatedEvent;
    private Optional<Account> accountOpt;
    private AccountCall accountCall;

    @BeforeEach
    public void init() {
        CallType callType = CallType.INPUT;
        Date startDate = new Date();
        Date endDate = new Date();
        Duration duration = Duration.ofMillis(100);
        double cost = 100;
        accountCallService = new AccountCallServiceImpl(accountRepository, accountCallRepository);
        account = new Account(7L, 100);
        account.setId(10L);
        accountOpt = Optional.of(account);

        callCostCalculatedEvent = new CallCostCalculatedEvent(
          account.getId(), callType, cost, startDate, endDate, duration
        );

        accountCall = new AccountCall(account, callType, startDate, endDate, duration, cost);
    }

    @Test
    public void should_add_call_with_positive_balance() throws BalanceLessThanZeroException, AccountNotFoundException {
        when(accountRepository.findById(account.getId())).thenReturn(accountOpt);
        when(accountCallRepository.save(any(AccountCall.class))).thenReturn(accountCall);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountCallService.addCall(callCostCalculatedEvent);

        verify(accountRepository, times(1)).findById(account.getId());
        verify(accountCallRepository, times(1)).save(any(AccountCall.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void should_not_add_call_with_negative_balance_exception() {
        account.setBalance(-100);
        when(accountRepository.findById(account.getId())).thenReturn(accountOpt);
        assertThrows(BalanceLessThanZeroException.class, () -> accountCallService.addCall(callCostCalculatedEvent));
    }

    @Test
    public void should_find_user_calls_returned_user_call_model() {
        long userId = 8L;
        List<AccountCall> accountCallList = List.of(accountCall);

        List<AccountCallResponseModel> callResponseModels =
          accountCallList
            .stream()
            .map(elem -> new AccountCallResponseModel(
              elem.getCallType(),
              elem.getStartDate(),
              elem.getEndDate(),
              elem.getDuration(),
              elem.getCost()))
            .collect(Collectors.toList());

        double totalAmount = accountCallList
          .stream()
          .mapToDouble(AccountCall::getCost)
          .sum();

        UserCallsModel expectedUserCallsModel = new UserCallsModel(callResponseModels, totalAmount);

        when(accountRepository.findByUserId(userId)).thenReturn(account);
        when(accountCallRepository.findByAccountId(account.getId())).thenReturn(accountCallList);

        UserCallsModel actualUserCallsModel = accountCallService.findUserCalls(userId);

        verify(accountRepository, times(1)).findByUserId(userId);
        verify(accountCallRepository, times(1)).findByAccountId(account.getId());

        assertEquals(
          expectedUserCallsModel.getAccountCallList().get(0).getCallType(),
          actualUserCallsModel.getAccountCallList().get(0).getCallType()
        );

        assertEquals(
          expectedUserCallsModel.getAccountCallList().get(0).getCost(),
          actualUserCallsModel.getAccountCallList().get(0).getCost()
        );

        assertEquals(
          expectedUserCallsModel.getAccountCallList().get(0).getDuration(),
          actualUserCallsModel.getAccountCallList().get(0).getDuration()
        );

        assertEquals(
          expectedUserCallsModel.getAccountCallList().get(0).getEndTime(),
          actualUserCallsModel.getAccountCallList().get(0).getEndTime()
        );

        assertEquals(
          expectedUserCallsModel.getAccountCallList().get(0).getStartTime(),
          actualUserCallsModel.getAccountCallList().get(0).getStartTime()
        );

        assertEquals(expectedUserCallsModel.getTotalAmount(), actualUserCallsModel.getTotalAmount());
    }
}
