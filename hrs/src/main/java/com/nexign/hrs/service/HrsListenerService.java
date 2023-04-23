package com.nexign.hrs.service;

import com.nexign.hrs.exception.AboveTariffRateNotFoundException;

public interface HrsListenerService {

    void processMessage(String callAuthorizedModel) throws AboveTariffRateNotFoundException;
}
