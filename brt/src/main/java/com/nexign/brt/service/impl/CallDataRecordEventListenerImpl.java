package com.nexign.brt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.service.CallDataRecordEventListener;
import com.nexign.brt.service.EventHandler;
import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.common.model.CallRecordModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CallDataRecordEventListenerImpl implements CallDataRecordEventListener {

    private ObjectMapper objectMapper;
    private JmsTemplate jmsTemplate;
    private EventHandler eventHandler;
    private @Value("${brt.queue}") String brtQueue;

    public CallDataRecordEventListenerImpl(ObjectMapper objectMapper, JmsTemplate jmsTemplate,
                                           EventHandler eventHandler) {
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
        this.eventHandler = eventHandler;
    }

    @Override
    @JmsListener(destination = "cdr-queue")
    public void processMessage(String cdrModel) throws BalanceLessThanZeroException {
        try {
            CallRecordModel event = objectMapper.readValue(cdrModel, CallRecordModel.class);
            CallAuthorizedEvent cam = eventHandler.cdrHandle(event);
            jmsTemplate.convertAndSend(brtQueue, objectMapper.writeValueAsString(cam));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
