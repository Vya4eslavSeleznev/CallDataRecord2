package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.brt.entity.AccountCall;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.AccountCallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AccountCallServiceImpl implements AccountCallService {

    private AccountRepository accountRepository;
    private AccountCallRepository accountCallRepository;

    @Override
    public void addCall(CallCostCalculatedEvent costCalculatedEvent) throws BalanceLessThanZeroException {
        Account account = accountRepository.findById(costCalculatedEvent.getAccountId()).get();

        if(account.getBalance() <= 0) {
            throw new BalanceLessThanZeroException("Balance less than zero");
        }

        double oldBalance = account.getBalance();
        account.setBalance(oldBalance - costCalculatedEvent.getCost());
        accountRepository.save(account);

        Date startDate = costCalculatedEvent.getStartDate();
        Date endDate = costCalculatedEvent.getEndDate();
        Date duration = new Date(getDateDiff(startDate, endDate));

        accountCallRepository.save(
          new AccountCall(account, costCalculatedEvent.getCallType(), startDate, endDate, duration, costCalculatedEvent.getCost())
        );
    }

    private long getDateDiff(Date startDate, Date endDate) {
        long diffInMollies = endDate.getTime() - startDate.getTime();
        return TimeUnit.SECONDS.convert(diffInMollies, TimeUnit.MILLISECONDS);
    }
}
