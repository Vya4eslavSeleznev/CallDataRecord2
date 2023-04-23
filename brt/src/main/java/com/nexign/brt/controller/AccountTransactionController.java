package com.nexign.brt.controller;

import com.nexign.brt.entity.AccountTransaction;
import com.nexign.brt.model.PaymentModel;
import com.nexign.brt.service.AccountTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class AccountTransactionController {

    private AccountTransactionService accountTransactionService;

    @PatchMapping
    public ResponseEntity<AccountTransaction> customerPayment(@RequestBody PaymentModel paymentModel) {
        AccountTransaction accountTransaction =
          accountTransactionService.addTransaction(paymentModel.getPhoneNumber(),paymentModel.getAmount());

        return new ResponseEntity<>(accountTransaction, HttpStatus.OK);
    }
}
