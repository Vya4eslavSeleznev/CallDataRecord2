package com.nexign.hrs.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.hrs.model.CallAuthorizedModel;
import com.nexign.hrs.service.HrsListenerService;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HrsListenerServiceImpl implements HrsListenerService {

    private ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "brt-queue")
    public void processMessage(String callAuthorizedModel) {
        try {
            CallAuthorizedModel event = objectMapper.readValue(callAuthorizedModel, CallAuthorizedModel.class);
            System.out.println(event);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
