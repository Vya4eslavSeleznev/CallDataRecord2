package com.nexign.hrs.service;

import com.nexign.common.model.*;
import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.service.Impl.CalculateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculateServiceTest {

    @InjectMocks
    private CalculateServiceImpl calculateService;

    @Mock
    private TariffGateway tariffGateway;

    private CallAuthorizedEvent callAuthorizedEvent;
    private CallType callType;
    private long diff;
    private CallCostCalculatedEvent expectedCallCostCalculatedEvent;

    @BeforeEach
    public void init() {
        calculateService = new CalculateServiceImpl(tariffGateway);
        callType = CallType.INPUT;
        long accountId = 5L;

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

        callAuthorizedEvent = new CallAuthorizedEvent(
          callType, accountId, 4L, startDate, endDate, 200
        );

        diff = endDate.getTime() - startDate.getTime();

        expectedCallCostCalculatedEvent = new CallCostCalculatedEvent(accountId, callType, -1, startDate, endDate, Duration.ofMillis(diff)
        );
    }

    @Test
    public void should_calculate_with_prepaid_tariff_returned_call_cost_calculated_event()
      throws AboveTariffRateNotFoundException {
        List<TariffInfoModel> tariffList = List.of(
          new TariffInfoModel(300, 100, 1L, TariffType.PREPAID, callType)
        );

        double callCost = (
          tariffList.get(0).getCost()
          * Duration.ofMillis(diff).toMinutes()
        ) / tariffList.get(0).getInterval();

        expectedCallCostCalculatedEvent.setCost(callCost);

        when(tariffGateway.getTariffInfo(callAuthorizedEvent.getTariffId(),
          callAuthorizedEvent.getCallType())).thenReturn(tariffList);

        CallCostCalculatedEvent actualCallCostCalculatedEvent = calculateService.calculation(callAuthorizedEvent);

        verify(tariffGateway, times(1)).getTariffInfo(callAuthorizedEvent.getTariffId(),
          callAuthorizedEvent.getCallType());

        assertEquals(expectedCallCostCalculatedEvent.getCost(), actualCallCostCalculatedEvent.getCost());
    }

    @Test
    public void should_calculate_with_postpaid_tariff_returned_call_cost_calculated_event()
      throws AboveTariffRateNotFoundException {
        List<TariffInfoModel> tariffList = List.of(
          new TariffInfoModel(1, 1.5, 1L, TariffType.POSTPAID, callType)
        );

        double callCost = (
          Duration.ofMillis(diff).toSeconds() * tariffList.get(0).getCost()
        ) / (tariffList.get(0).getInterval() * 60);

        expectedCallCostCalculatedEvent.setCost(callCost);

        when(tariffGateway.getTariffInfo(callAuthorizedEvent.getTariffId(),
          callAuthorizedEvent.getCallType())).thenReturn(tariffList);

        CallCostCalculatedEvent actualCallCostCalculatedEvent = calculateService.calculation(callAuthorizedEvent);

        verify(tariffGateway, times(1)).getTariffInfo(callAuthorizedEvent.getTariffId(),
          callAuthorizedEvent.getCallType());

        assertEquals(expectedCallCostCalculatedEvent.getCost(), actualCallCostCalculatedEvent.getCost());
    }

    @Test
    public void should_calculate_without_tariff_exception() {
        List<TariffInfoModel> tariffList = new ArrayList<>();

        when(tariffGateway.getTariffInfo(callAuthorizedEvent.getTariffId(),
          callAuthorizedEvent.getCallType())).thenReturn(tariffList);
        assertThrows(AboveTariffRateNotFoundException.class, () -> calculateService.calculation(callAuthorizedEvent));
    }
}
