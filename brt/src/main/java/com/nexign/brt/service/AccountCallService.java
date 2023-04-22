package com.nexign.brt.service;

import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;

public interface AccountCallService {

    void addCall(CallCostCalculatedEvent costCalculatedEvent) throws BalanceLessThanZeroException;
}
