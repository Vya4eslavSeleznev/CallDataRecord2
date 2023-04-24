package com.nexign.brt.service;

import com.nexign.brt.model.CreateAccountRequestModel;
import com.nexign.brt.model.UserBalanceModel;

import java.util.List;

public interface AccountService {

    void createAccount(CreateAccountRequestModel createAccountRequestModel);
    List<UserBalanceModel> getAccounts();
}
