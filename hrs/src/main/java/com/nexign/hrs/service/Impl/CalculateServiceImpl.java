package com.nexign.hrs.service.Impl;

import com.nexign.common.model.*;
import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
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
    public CallCostCalculatedEvent calculation(CallAuthorizedEvent event) throws AboveTariffRateNotFoundException {
        List<TariffInfoModel> tariffList = tariffGateway.getTariffInfo(
          event.getTariffId(),
          event.getCallType()
        );

        Optional<TariffInfoModel> tariffInfoOpt = findTariff(tariffList, TariffType.PREPAID, event.getCallType());
        Optional<TariffInfoModel> tariffAboveInfoOpt = findTariff(tariffList, TariffType.POSTPAID, event.getCallType());

        if(tariffInfoOpt.isPresent() && event.getMinutesSpent() < tariffInfoOpt.get().getInterval()) {
            return calculationInternal(event, tariffInfoOpt.get());
        } else if(tariffAboveInfoOpt.isPresent() && event.getMinutesSpent() >= tariffAboveInfoOpt.get().getInterval()) {
            return calculationInternal(event, tariffAboveInfoOpt.get());
        } else {
            throw new AboveTariffRateNotFoundException();
        }
    }

    private Optional<TariffInfoModel> findTariff(List<TariffInfoModel> tariffList, TariffType tariffType,
                                                 CallType callType) {
        return tariffList.stream()
          .filter(trf -> trf.getTariffType() == tariffType && trf.getCallType() == callType)
          .findFirst();
    }

    private CallCostCalculatedEvent calculationInternal(CallAuthorizedEvent event, TariffInfoModel tariffInfo) {
        CallCostCalculatedEvent calculatedEvent = new CallCostCalculatedEvent();

        calculatedEvent.setAccountId(event.getAccountId());
        calculatedEvent.setCallType(event.getCallType());

        Date startDate = event.getStartDate();
        Date endDate = event.getEndDate();

        calculatedEvent.setStartDate(startDate);
        calculatedEvent.setEndDate(endDate);

        calculatedEvent.setDuration(Duration.ofMillis(endDate.getTime() - startDate.getTime()));

        long tarifficationIntervalSeconds = TimeUnit.MINUTES.toSeconds(tariffInfo.getInterval());
        long callSeconds = Duration.ofMillis(endDate.getTime() - startDate.getTime()).toSeconds();
        double totalCost = (tariffInfo.getCost() * callSeconds) / tarifficationIntervalSeconds;

        calculatedEvent.setCost(totalCost);

        return calculatedEvent;
    }
}
