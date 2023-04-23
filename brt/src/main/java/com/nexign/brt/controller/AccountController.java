package com.nexign.brt.controller;

import com.nexign.brt.model.BalanceUpdateByPhone;
import com.nexign.brt.model.CreateAccountRequestModel;
import com.nexign.brt.service.AccountService;
import com.nexign.brt.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountTransactionService accountTransactionService;
    private AccountService accountService;

    @PostMapping("/balance")
    public void updateBalanceByPhone(@RequestBody BalanceUpdateByPhone balanceUpdateByPhone) {
        accountTransactionService.addTransaction(balanceUpdateByPhone.getPhoneNumber(), balanceUpdateByPhone.getAmount());
    }

    @PostMapping
    public void createAccount(@RequestBody CreateAccountRequestModel createAccountRequestModel) {
        accountService.createAccount(createAccountRequestModel);
    }
}
