package com.nexign.crm.service;

import com.nexign.common.model.FindByPhoneModel;
import com.nexign.common.model.UserBalanceModel;
import com.nexign.common.model.UserCallsModel;

public interface CrmGateway {

    FindByPhoneModel findByPhone(String phoneNumber);
    UserCallsModel getUserCalls(FindByPhoneModel findByPhoneModel);
    UserBalanceModel[] getUserBalances();
}
