package com.nexign.hrs.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.model.*;
import com.nexign.hrs.service.CalculateService;
import com.nexign.hrs.service.HrsListenerService;
import com.nexign.hrs.service.TariffGateway;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class HrsListenerServiceImpl implements HrsListenerService {

    private ObjectMapper objectMapper;
    //private TariffGateway tariffGateway;
    private CalculateService calculateService;
    private final JmsTemplate jmsTemplate;
    private final String hrsQueue;

    public HrsListenerServiceImpl(ObjectMapper objectMapper, CalculateService calculateService, JmsTemplate jmsTemplate,
                                  @Value("${hrs.queue}") String hrsQueue) {
        this.objectMapper = objectMapper;
        this.calculateService = calculateService;
        this.jmsTemplate = jmsTemplate;
        this.hrsQueue = hrsQueue;
    }

    @Override
    @JmsListener(destination = "brt-queue")
    public void processMessage(String callAuthorizedModel) throws AboveTariffRateNotFoundException {
        try {
            CallAuthorizedModel event = objectMapper.readValue(callAuthorizedModel, CallAuthorizedModel.class);
//            List<TariffInfoModel> tariffList = tariffGateway.getTariffInfo(
//              event.getTariffId(),
//              event.getCallType()
//            );
//
//            Optional<TariffInfoModel> tariffInfoOpt = findTariff(tariffList, TariffType.PREPAID, event.getCallType());
//            Optional<TariffInfoModel> tariffAboveInfoOpt = findTariff(tariffList, TariffType.POSTPAID, event.getCallType());
//            CallCostCalculatedEvent calculatedEvent = new CallCostCalculatedEvent();
//
//            if(tariffInfoOpt.isPresent() && event.getMinutesSpent() < tariffInfoOpt.get().getInterval()) {
//                calculation(calculatedEvent, event, tariffInfoOpt.get());
//            }
//
//            if(tariffAboveInfoOpt.isPresent() && event.getMinutesSpent() >= tariffAboveInfoOpt.get().getInterval()) {
//                calculation(calculatedEvent, event, tariffAboveInfoOpt.get());
//            } else if(tariffAboveInfoOpt.isEmpty()) {
//                throw new AboveTariffRateNotFoundException("Tariff above rate not found");
//            }

            CallCostCalculatedEvent calculatedEvent = calculateService.calculation(event);
            jmsTemplate.convertAndSend(hrsQueue, objectMapper.writeValueAsString(calculatedEvent));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

//    private Optional<TariffInfoModel> findTariff(List<TariffInfoModel> tariffList, TariffType tariffType,
//                                                 CallType callType) {
//        return tariffList.stream()
//          .filter(trf -> trf.getTariffType() == tariffType && trf.getCallType() == callType)
//          .findFirst();
//    }
//
//    private void calculation(CallCostCalculatedEvent calculatedEvent, CallAuthorizedModel event, TariffInfoModel tariffInfo) {
//        calculatedEvent.setAccountId(event.getAccountId());
//        calculatedEvent.setCallType(event.getCallType());
//
//        Date startDate = event.getStartDate();
//        Date endDate = event.getEndDate();
//
//        calculatedEvent.setStartDate(startDate);
//        calculatedEvent.setEndDate(endDate);
//
//        calculatedEvent.setDuration(Duration.ofMillis(endDate.getTime() - startDate.getTime()));
//
//        long tariffMillis = TimeUnit.MINUTES.toMillis(tariffInfo.getInterval());
//        double tariffMinutesCost = tariffInfo.getCost();
//
//        long callMillis = Duration.of(endDate.getTime() - startDate.getTime(), ChronoUnit.SECONDS).toMillis();
//        double totalCost = (tariffMinutesCost * callMillis) / tariffMillis;
//
//        calculatedEvent.setCost(totalCost);
//    }
}
