package com.nexign.brt.service;

import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.model.UserCallsModel;

public interface AccountCallService {

    void addCall(CallCostCalculatedEvent costCalculatedEvent)
      throws BalanceLessThanZeroException, AccountNotFoundException;

    UserCallsModel findUserCalls(long userId);
}
