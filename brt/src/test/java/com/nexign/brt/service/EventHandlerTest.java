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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    private Date startDate;
    private Date endDate;
    private CallRecordModel event;
    private FindByPhoneModel userInfo;
    private long tariffId;
    private CallType callType;
    private Account account;

    @BeforeEach
    public void init() {
        eventHandler = new EventHandlerImpl(userGateway, accountRepository, accountCallRepository);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 10);
        cal.set(Calendar.MINUTE, 10);
        cal.set(Calendar.SECOND, 10);

        startDate = cal.getTime();

        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 10);
        cal.set(Calendar.MINUTE, 12);
        cal.set(Calendar.SECOND, 10);

        endDate = cal.getTime();
        callType = CallType.INPUT;
        long userId = 7L;
        tariffId = 7L;

        userInfo = new FindByPhoneModel(userId, tariffId);
        event = new CallRecordModel(callType, "89997776655", startDate, endDate);
        account = new Account(userId, 100L);
    }

    @Test
    public void should_handle_cdr_returned_call_authorized_event() throws BalanceLessThanZeroException {
        long spentMinutes = 777;

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

    @Test
    public void should_handle_cdr_returned_exception() {
        when(userGateway.getUserInfo(event.getPhoneNumber())).thenReturn(userInfo);
        when(accountRepository.findByUserId(userInfo.getUserId())).thenReturn(account);
        account.setBalance(-100L);
        assertThrows(BalanceLessThanZeroException.class, () -> eventHandler.cdrHandle(event));
    }
}
