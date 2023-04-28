package com.nexign.brt.controller;

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
    public void updateBalanceByPhone(@RequestBody PhoneAndBalanceModel phoneAndBalanceModel) {
        accountTransactionService.addTransaction(phoneAndBalanceModel.getPhoneNumber(), phoneAndBalanceModel.getBalance());
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
