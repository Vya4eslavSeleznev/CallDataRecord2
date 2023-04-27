package com.nexign.brt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt.entity.Account;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.common.model.CallAuthorizedEvent;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.FindByPhoneModel;
import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.CallDataRecordEventListener;
import com.nexign.brt.service.UserGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CallDataRecordEventListenerImpl implements CallDataRecordEventListener {

    private ObjectMapper objectMapper;
    private AccountRepository accountRepository;
    private final JmsTemplate jmsTemplate;
    private final String brtQueue;
    private UserGateway userGateway;
    private AccountCallRepository accountCallRepository;

    public CallDataRecordEventListenerImpl(ObjectMapper objectMapper, AccountRepository accountRepository,
                                           JmsTemplate jmsTemplate, @Value("${brt.queue}") String brtQueue,
                                           UserGateway userGateway,
                                           AccountCallRepository accountCallRepository) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
        this.jmsTemplate = jmsTemplate;
        this.brtQueue = brtQueue;
        this.userGateway = userGateway;
        this.accountCallRepository = accountCallRepository;
    }

    @Override
    @JmsListener(destination = "cdr-queue")
    public void processMessage(String cdrModel) throws BalanceLessThanZeroException {
        try {
            CallRecordModel event = objectMapper.readValue(cdrModel, CallRecordModel.class);
            FindByPhoneModel userInfo = userGateway.getUserInfo(event.getPhoneNumber());
            Account account = accountRepository.findByUserId(userInfo.getUserId());

            if(account.getBalance() <= 0) {
                throw new BalanceLessThanZeroException();
            }

            LocalDate currentDate = LocalDate.now();

            Optional<Long> minutesSpentOpt = accountCallRepository.findByAccountIdAndDate(account.getId(),
              currentDate.getMonthValue(), currentDate.getYear());

            CallAuthorizedEvent cam = new CallAuthorizedEvent(
              event.getCallType(),
              account.getId(),
              userInfo.getTariffId(),
              event.getStartDate(),
              event.getEndDate(),
              minutesSpentOpt.isPresent() ? minutesSpentOpt.get() : 0
            );

            jmsTemplate.convertAndSend(brtQueue, objectMapper.writeValueAsString(cam));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
