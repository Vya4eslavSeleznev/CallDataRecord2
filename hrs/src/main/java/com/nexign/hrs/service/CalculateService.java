package com.nexign.hrs.service;

import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.common.model.CallCostCalculatedEvent;

public interface CalculateService {

    CallCostCalculatedEvent calculation(CallAuthorizedEvent event) throws AboveTariffRateNotFoundException;
}
