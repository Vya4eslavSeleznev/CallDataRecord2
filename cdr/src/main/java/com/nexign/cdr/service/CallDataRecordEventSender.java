package com.nexign.cdr.service;

import com.nexign.common.model.CallRecordModel;

import java.util.List;

public interface CallDataRecordEventSender {

    void sendEvents(List<CallRecordModel> cdrListModel);
}
