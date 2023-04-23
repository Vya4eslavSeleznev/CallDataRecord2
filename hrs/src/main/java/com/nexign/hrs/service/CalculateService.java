package com.nexign.hrs.service;

import com.nexign.hrs.exception.AboveTariffRateNotFoundException;
import com.nexign.hrs.model.CallAuthorizedModel;
import com.nexign.hrs.model.CallCostCalculatedEvent;

public interface CalculateService {

    CallCostCalculatedEvent calculation(CallAuthorizedModel event) throws AboveTariffRateNotFoundException;
}
