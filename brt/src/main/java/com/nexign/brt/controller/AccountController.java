package com.nexign.brt.controller;

import com.nexign.brt.model.BalanceUpdateByPhone;
import com.nexign.brt.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountTransactionService accountTransactionService;

    @PostMapping("/balance")
    public void updateBalanceByPhone(@RequestBody BalanceUpdateByPhone balanceUpdateByPhone) {
        accountTransactionService.addTransaction(balanceUpdateByPhone.getPhoneNumber(), balanceUpdateByPhone.getAmount());
    }
}
