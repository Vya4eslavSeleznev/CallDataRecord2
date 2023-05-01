package com.nexign.brt.service;

import com.nexign.brt.exception.PaymentLessThanZeroException;

public interface AccountTransactionService {

    long addTransaction(String phoneNumber, double amount) throws PaymentLessThanZeroException;
}
