package com.nexign.cdr.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.common.model.CallRecordModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallDataRecordEventSenderImpl implements CallDataRecordEventSender {

    private final JmsTemplate jmsTemplate;
    private final String cdrQueue;
    private ObjectMapper objectMapper;

    public CallDataRecordEventSenderImpl(JmsTemplate jmsTemplate, @Value("${cdr.queue}") String cdrQueue,
                                         ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.cdrQueue = cdrQueue;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendEvents(List<CallRecordModel> cdrListModel) {
        cdrListModel.forEach(cdr -> {
            try {
                jmsTemplate.convertAndSend(cdrQueue, objectMapper.writeValueAsString(cdr));
            }
            catch(JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
