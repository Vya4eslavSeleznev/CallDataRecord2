package com.nexign.brt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.service.AccountCallService;
import com.nexign.brt.service.HrsListenerService;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HrsListenerServiceImpl implements HrsListenerService {

    private ObjectMapper objectMapper;
    private AccountCallService accountCallService;

    @Override
    @JmsListener(destination = "hrs-queue")
    public void processMessage(String hrsModel) {
        try {
            CallCostCalculatedEvent event = objectMapper.readValue(hrsModel, CallCostCalculatedEvent.class);
            accountCallService.addCall(event);
        }
        catch(BalanceLessThanZeroException | AccountNotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
