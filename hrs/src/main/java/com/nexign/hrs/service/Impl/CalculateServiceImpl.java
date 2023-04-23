package com.nexign.hrs.service.Impl;

import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.model.*;
import com.nexign.hrs.service.CalculateService;
import com.nexign.hrs.service.TariffGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    private TariffGateway tariffGateway;

    @Override
    public CallCostCalculatedEvent calculation(CallAuthorizedModel event) throws AboveTariffRateNotFoundException {
        List<TariffInfoModel> tariffList = tariffGateway.getTariffInfo(
          event.getTariffId(),
          event.getCallType()
        );

        Optional<TariffInfoModel> tariffInfoOpt = findTariff(tariffList, TariffType.PREPAID, event.getCallType());
        Optional<TariffInfoModel> tariffAboveInfoOpt = findTariff(tariffList, TariffType.POSTPAID, event.getCallType());
        CallCostCalculatedEvent calculatedEvent = new CallCostCalculatedEvent();

        if(tariffInfoOpt.isPresent() && event.getMinutesSpent() < tariffInfoOpt.get().getInterval()) {
            calculation(calculatedEvent, event, tariffInfoOpt.get());
        }

        if(tariffAboveInfoOpt.isPresent() && event.getMinutesSpent() >= tariffAboveInfoOpt.get().getInterval()) {
            calculation(calculatedEvent, event, tariffAboveInfoOpt.get());
        } else if(tariffAboveInfoOpt.isEmpty()) {
            throw new AboveTariffRateNotFoundException("Tariff above rate not found");
        }

        return calculatedEvent;
    }

    private Optional<TariffInfoModel> findTariff(List<TariffInfoModel> tariffList, TariffType tariffType,
                                                 CallType callType) {
        return tariffList.stream()
          .filter(trf -> trf.getTariffType() == tariffType && trf.getCallType() == callType)
          .findFirst();
    }

    private void calculation(CallCostCalculatedEvent calculatedEvent, CallAuthorizedModel event, TariffInfoModel tariffInfo) {
        calculatedEvent.setAccountId(event.getAccountId());
        calculatedEvent.setCallType(event.getCallType());

        Date startDate = event.getStartDate();
        Date endDate = event.getEndDate();

        calculatedEvent.setStartDate(startDate);
        calculatedEvent.setEndDate(endDate);

        calculatedEvent.setDuration(Duration.ofMillis(endDate.getTime() - startDate.getTime()));

        long tariffSeconds = TimeUnit.MINUTES.toSeconds(tariffInfo.getInterval());
        double tariffMinutesCost = tariffInfo.getCost();

        long callSeconds = Duration.of(endDate.getTime() - startDate.getTime(), ChronoUnit.SECONDS).toSeconds();
        double totalCost = (tariffMinutesCost * callSeconds) / tariffSeconds;

        calculatedEvent.setCost(totalCost);
    }
}
