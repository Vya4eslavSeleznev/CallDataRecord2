package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.brt.model.CreateAccountRequestModel;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Override
    public void createAccount(CreateAccountRequestModel createAccountRequestModel) {
        accountRepository.save(
          new Account(createAccountRequestModel.getUserId(), createAccountRequestModel.getBalance())
        );
    }
}
