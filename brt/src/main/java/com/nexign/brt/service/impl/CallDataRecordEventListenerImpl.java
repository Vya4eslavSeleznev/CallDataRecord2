package com.nexign.brt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt.entity.Account;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallAuthorizedModel;
import com.nexign.brt.model.CallRecordModel;
import com.nexign.brt.model.FindByPhoneModel;
import com.nexign.brt.repository.AccountRepository;
import com.nexign.brt.service.CallDataRecordEventListener;
import com.nexign.brt.service.UserGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CallDataRecordEventListenerImpl implements CallDataRecordEventListener {

    private ObjectMapper objectMapper;
    private AccountRepository accountRepository;
    private final JmsTemplate jmsTemplate;
    private final String brtQueue;
    private UserGateway userGateway;

    public CallDataRecordEventListenerImpl(ObjectMapper objectMapper, AccountRepository accountRepository,
                                           JmsTemplate jmsTemplate, @Value("${brt.queue}") String brtQueue,
                                           UserGateway userGateway) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
        this.jmsTemplate = jmsTemplate;
        this.brtQueue = brtQueue;
        this.userGateway = userGateway;
    }

    @Override
    @JmsListener(destination = "cdr-queue")
    public void processMessage(String cdrModel) throws BalanceLessThanZeroException {
        try {
            CallRecordModel event = objectMapper.readValue(cdrModel, CallRecordModel.class);
            FindByPhoneModel userInfo = userGateway.getUserInfo(event.getPhoneNumber());
            Account account = accountRepository.findByUserId(userInfo.getUserId());

            if(account.getBalance() <= 0) {
                throw new BalanceLessThanZeroException("Balance less than zero");
            }

            CallAuthorizedModel cam = new CallAuthorizedModel(
              event.getCallType(),
              account.getId(),
              userInfo.getTariffId(),
              event.getStartDate(),
              event.getEndDate(),
              0
            );

            jmsTemplate.convertAndSend(brtQueue, objectMapper.writeValueAsString(cam));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
