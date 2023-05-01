package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountTransaction;
import com.nexign.brt.exception.PaymentLessThanZeroException;
import com.nexign.common.model.FindByPhoneModel;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.repository.AccountTransactionRepository;
import com.nexign.brt.service.AccountTransactionService;
import com.nexign.brt.service.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private UserGateway userGateway;
    private AccountTransactionRepository accountTransactionRepository;
    private AccountRepository accountRepository;

    @Override
    public long addTransaction(String phoneNumber, double amount) throws PaymentLessThanZeroException {
        if(amount <= 0) {
            throw new PaymentLessThanZeroException();
        }

        FindByPhoneModel userInfo = userGateway.getUserInfo(phoneNumber);
        Account account = accountRepository.findByUserId(userInfo.getUserId());

        double oldBalance = account.getBalance();
        account.setBalance(oldBalance + amount);
        accountRepository.save(account);

        return accountTransactionRepository.save(
          new AccountTransaction(account, amount)
        ).getId();
    }
}
