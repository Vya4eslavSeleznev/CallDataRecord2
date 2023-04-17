package com.nexign.brt.service;

import com.nexign.brt.model.CallRecordModel;
import org.springframework.messaging.handler.annotation.Payload;

public interface CallDataRecordEventListener {

    void listenEvent(@Payload CallRecordModel cdrModel);
}
