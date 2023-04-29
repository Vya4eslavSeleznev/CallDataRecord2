package com.nexign.brt.service;

import com.nexign.brt.entity.Account;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.impl.EventHandlerImpl;
import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.CallType;
import com.nexign.common.model.FindByPhoneModel;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EventHandlerTest {

    @InjectMocks
    private EventHandlerImpl eventHandler;

    @Mock
    private UserGateway userGateway;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountCallRepository accountCallRepository;

    @BeforeEach
    public void init() {
        eventHandler = new EventHandlerImpl(userGateway, accountRepository, accountCallRepository);
    }

    @Test
    public void cdr_handle() throws BalanceLessThanZeroException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 10);
        cal.set(Calendar.MINUTE, 10);
        cal.set(Calendar.SECOND, 10);

        Date startDate = cal.getTime();

        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 10);
        cal.set(Calendar.MINUTE, 12);
        cal.set(Calendar.SECOND, 10);

        Date endDate = cal.getTime();

        long userId = 7L;
        long spentMinutes = 777;
        CallType callType = CallType.INPUT;
        long tariffId = 7L;

        CallRecordModel event = new CallRecordModel(callType, "89997776655", startDate, endDate);
        FindByPhoneModel userInfo = new FindByPhoneModel(userId, tariffId);
        Account account = new Account(userId, 100L);
        account.setId(10L);
        LocalDate currentDate = LocalDate.now();

        CallAuthorizedEvent expectedCallAuthorizedEvent = new CallAuthorizedEvent(
          callType, account.getId(), tariffId, startDate, endDate, spentMinutes
        );

        when(userGateway.getUserInfo(event.getPhoneNumber())).thenReturn(userInfo);
        when(accountRepository.findByUserId(userInfo.getUserId())).thenReturn(account);
        when(accountCallRepository.findByAccountIdAndDate(account.getId(),
          currentDate.getMonthValue(), currentDate.getYear())).thenReturn(Optional.of(spentMinutes));

        CallAuthorizedEvent actualCallAuthorizedEvent = eventHandler.cdrHandle(event);

        verify(userGateway, times(1)).getUserInfo(event.getPhoneNumber());
        verify(accountRepository, times(1)).findByUserId(userInfo.getUserId());
        verify(accountCallRepository, times(1)).findByAccountIdAndDate(account.getId(),
          currentDate.getMonthValue(), currentDate.getYear());

        assertEquals(expectedCallAuthorizedEvent.getCallType(), actualCallAuthorizedEvent.getCallType());
        assertEquals(expectedCallAuthorizedEvent.getTariffId(), actualCallAuthorizedEvent.getTariffId());
        assertEquals(expectedCallAuthorizedEvent.getMinutesSpent(), actualCallAuthorizedEvent.getMinutesSpent());
        assertEquals(expectedCallAuthorizedEvent.getAccountId(), actualCallAuthorizedEvent.getAccountId());
        assertEquals(expectedCallAuthorizedEvent.getEndDate(), actualCallAuthorizedEvent.getEndDate());
        assertEquals(expectedCallAuthorizedEvent.getStartDate(), actualCallAuthorizedEvent.getStartDate());

    }
}
