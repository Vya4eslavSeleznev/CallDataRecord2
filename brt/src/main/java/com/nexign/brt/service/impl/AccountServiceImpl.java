package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.common.model.CreateAccountRequestModel;
import com.nexign.brt.model.UserBalanceInterface;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.AccountService;
import com.nexign.common.model.UserBalanceModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserBalanceModel> getAccounts() {
        List<UserBalanceInterface> accountList = accountRepository.findAllBy();

        return accountList
          .stream()
          .map(elem -> new UserBalanceModel(
            elem.getUserId(),
            elem.getBalance()
          ))
          .collect(Collectors.toList());
    }
}
