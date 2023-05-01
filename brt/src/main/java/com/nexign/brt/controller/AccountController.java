package com.nexign.brt.controller;

import com.nexign.brt.exception.PaymentLessThanZeroException;
import com.nexign.common.model.CreateAccountRequestModel;
import com.nexign.brt.service.AccountService;
import com.nexign.brt.service.AccountTransactionService;
import com.nexign.common.model.PhoneAndBalanceModel;
import com.nexign.common.model.UserBalanceModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountTransactionService accountTransactionService;
    private AccountService accountService;

    @PostMapping("/balance")
    public ResponseEntity<?> updateBalanceByPhone(@RequestBody PhoneAndBalanceModel phoneAndBalanceModel) {
        try {
            accountTransactionService.addTransaction(phoneAndBalanceModel.getPhoneNumber(),
              phoneAndBalanceModel.getBalance());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(PaymentLessThanZeroException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public void createAccount(@RequestBody CreateAccountRequestModel createAccountRequestModel) {
        accountService.createAccount(createAccountRequestModel);
    }

    @GetMapping
    public ResponseEntity<List<UserBalanceModel>> getAccounts() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }
}
