package com.nexign.brt.service;

import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.common.model.CallCostCalculatedEvent;
import com.nexign.common.model.UserCallsModel;

public interface AccountCallService {

    void addCall(CallCostCalculatedEvent costCalculatedEvent)
      throws BalanceLessThanZeroException, AccountNotFoundException;

    UserCallsModel findUserCalls(long userId);
}
