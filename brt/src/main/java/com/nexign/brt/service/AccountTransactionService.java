package com.nexign.brt.service;

import com.nexign.brt.entity.AccountTransaction;

public interface AccountTransactionService {

    AccountTransaction addTransaction(String phoneNumber, double amount);
}
