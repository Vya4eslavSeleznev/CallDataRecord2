package com.nexign.cdr.service.impl;

import com.nexign.cdr.model.CallRecordModel;
import com.nexign.cdr.service.CallDataRecordEventSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CallDataRecordEventSenderImpl implements CallDataRecordEventSender {

    private final JmsTemplate jmsTemplate;
    private final String cdrQueue;

    public CallDataRecordEventSenderImpl(JmsTemplate jmsTemplate, @Value("${cdr.queue}") String cdrQueue) {
        this.jmsTemplate = jmsTemplate;
        this.cdrQueue = cdrQueue;
    }

    @Override
    public void sendEvent(CallRecordModel cdrModel) {
        jmsTemplate.convertAndSend(cdrQueue, cdrModel);
    }
}
