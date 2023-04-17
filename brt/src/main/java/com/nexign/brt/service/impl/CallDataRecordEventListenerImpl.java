package com.nexign.brt.service.impl;

import com.nexign.brt.model.CallRecordModel;
import com.nexign.brt.service.CallDataRecordEventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CallDataRecordEventListenerImpl implements CallDataRecordEventListener {

    @Override
    @JmsListener(destination = "cdr-queue")
    public void listenEvent(@Payload CallRecordModel cdrModel) {
        System.out.println(cdrModel.getPhoneNumber());
    }
}
