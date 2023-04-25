package com.nexign.hrs.service;

import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.model.CallAuthorizedEvent;
import com.nexign.hrs.model.CallCostCalculatedEvent;

public interface CalculateService {

    CallCostCalculatedEvent calculation(CallAuthorizedEvent event) throws AboveTariffRateNotFoundException;
}
