package com.nexign.hrs.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.model.CallAuthorizedEvent;
import com.nexign.hrs.model.CallCostCalculatedEvent;
import com.nexign.hrs.service.CalculateService;
import com.nexign.hrs.service.HrsListenerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class HrsListenerServiceImpl implements HrsListenerService {

    private ObjectMapper objectMapper;
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
            CallAuthorizedEvent event = objectMapper.readValue(callAuthorizedModel, CallAuthorizedEvent.class);
            CallCostCalculatedEvent calculatedEvent = calculateService.calculation(event);
            jmsTemplate.convertAndSend(hrsQueue, objectMapper.writeValueAsString(calculatedEvent));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
