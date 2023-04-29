package com.nexign.brt.service.impl;

import com.nexign.brt.entity.Account;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.EventHandler;
import com.nexign.brt.service.UserGateway;
import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.FindByPhoneModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventHandlerImpl implements EventHandler {

    private UserGateway userGateway;
    private AccountRepository accountRepository;
    private AccountCallRepository accountCallRepository;

    @Override
    public CallAuthorizedEvent cdrHandle(CallRecordModel event) throws BalanceLessThanZeroException {
        FindByPhoneModel userInfo = userGateway.getUserInfo(event.getPhoneNumber());
        Account account = accountRepository.findByUserId(userInfo.getUserId());

        if(account.getBalance() <= 0) {
            throw new BalanceLessThanZeroException();
        }

        LocalDate currentDate = LocalDate.now();

        Optional<Long> minutesSpentOpt = accountCallRepository.findByAccountIdAndDate(account.getId(),
          currentDate.getMonthValue(), currentDate.getYear());

        return new CallAuthorizedEvent(
          event.getCallType(),
          account.getId(),
          userInfo.getTariffId(),
          event.getStartDate(),
          event.getEndDate(),
          minutesSpentOpt.isPresent() ? minutesSpentOpt.get() : 0
        );
    }
}
