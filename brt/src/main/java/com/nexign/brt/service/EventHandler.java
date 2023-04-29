package com.nexign.brt.service;

import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.common.model.CallRecordModel;

public interface EventHandler {

    CallAuthorizedEvent cdrHandle(CallRecordModel event) throws BalanceLessThanZeroException;
}
