package com.nexign.brt.controller;

import com.nexign.brt.entity.AccountTransaction;
import com.nexign.brt.model.PaymentModel;
import com.nexign.brt.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class AccountTransactionController {

    private AccountTransactionService accountTransactionService;

    @PutMapping
    public ResponseEntity<AccountTransaction> customerPayment(@RequestBody PaymentModel paymentModel) {
        AccountTransaction accountTransaction =
          accountTransactionService.addTransaction(paymentModel.getPhoneNumber(),paymentModel.getAmount());

        return new ResponseEntity<>(accountTransaction, HttpStatus.OK);
    }
}
