package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountCall;
import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.model.UserCallsModel;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.AccountCallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountCallServiceImpl implements AccountCallService {

    private AccountRepository accountRepository;
    private AccountCallRepository accountCallRepository;

    @Override
    @Transactional
    public void addCall(CallCostCalculatedEvent costCalculatedEvent)
      throws BalanceLessThanZeroException, AccountNotFoundException {
         Optional<Account> accountOptional = accountRepository.findById(costCalculatedEvent.getAccountId());

         if(accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
         }

        Account account = accountOptional.get();

        if(account.getBalance() <= 0) {
            throw new BalanceLessThanZeroException("Balance less than zero");
        }

        Date startDate = costCalculatedEvent.getStartDate();
        Date endDate = costCalculatedEvent.getEndDate();
        Duration duration = Duration.ofMillis(endDate.getTime() - startDate.getTime());

        accountCallRepository.save(
          new AccountCall(account, costCalculatedEvent.getCallType(), startDate, endDate, duration, costCalculatedEvent.getCost())
        );

        double oldBalance = account.getBalance();
        account.setBalance(oldBalance - costCalculatedEvent.getCost());
        accountRepository.save(account);
    }

    @Override
    public UserCallsModel findUserCalls(long userId) {
        Account account = accountRepository.findByUserId(userId);
        List<AccountCall> accountCallList = accountCallRepository.findByAccountId(account.getId());
        double totalAmount = accountCallList.stream().mapToDouble(AccountCall::getCost).sum();
        return new UserCallsModel(accountCallList, totalAmount);
    }
}
