package com.nexign.cdr.service;

import com.nexign.cdr.model.CallRecordModel;

public interface CallDataRecordEventSender {

    void sendEvent(CallRecordModel cdrModel);
}
