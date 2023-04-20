package com.nexign.brt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt.model.CallRecordModel;
import com.nexign.brt.service.CallDataRecordEventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class CallDataRecordEventListenerImpl implements CallDataRecordEventListener {

    private ObjectMapper objectMapper;

    public CallDataRecordEventListenerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @JmsListener(destination = "cdr-queue")
    public void processMessage(String cdrModel) {
        try {
            CallRecordModel event = objectMapper.readValue(cdrModel, CallRecordModel.class);
            System.out.println();
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}