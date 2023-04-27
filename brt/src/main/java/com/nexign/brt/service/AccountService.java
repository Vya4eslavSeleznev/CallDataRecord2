package com.nexign.brt.service;

import com.nexign.common.model.CreateAccountRequestModel;
import com.nexign.common.model.UserBalanceModel;

import java.util.List;

public interface AccountService {

    void createAccount(CreateAccountRequestModel createAccountRequestModel);
    List<UserBalanceModel> getAccounts();
}
