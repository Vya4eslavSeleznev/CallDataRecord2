package com.nexign.brt.controller;

import com.nexign.brt.model.PaymentModel;
import com.nexign.brt.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class AccountTransactionController {

    private AccountTransactionService accountTransactionService;

    @PutMapping
    public ResponseEntity<Long> customerPayment(@RequestBody PaymentModel paymentModel) {
        long accountTransactionId =
          accountTransactionService.addTransaction(paymentModel.getPhoneNumber(), paymentModel.getAmount());

        return new ResponseEntity<>(accountTransactionId, HttpStatus.OK);
    }
}
