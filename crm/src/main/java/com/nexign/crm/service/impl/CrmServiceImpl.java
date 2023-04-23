package com.nexign.crm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.crm.model.*;
import com.nexign.crm.service.CrmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CrmServiceImpl implements CrmService {

    private ObjectMapper objectMapper;
    private @Value("${brt.payment.url}") String brtPaymentUrl;
    private @Value("${user.tariff.url}") String userTariffUrl;
    private @Value("${user.info.url}") String userInfoUrl;
    private @Value("${brt.calls.url}") String brtCallsUrl;
    private @Value("${user.save.url}") String saveUserUrl;
    private @Value("${brt.account.url}") String createAccountUrl;

    public CrmServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public PaymentResponseModel callBrtPayment(PaymentModel paymentModel) {
        String resultId = callUrl(paymentModel, brtPaymentUrl, HttpMethod.PUT).getBody();

        if(resultId == null) {
            return null;
        }

        return new PaymentResponseModel(
          Long.parseLong(resultId),
          paymentModel.getPhoneNumber(),
          paymentModel.getAmount()
        );
    }

    @Override
    public ChangeTariffResponseModel changeTariff(ChangeTariffModel changeTariffModel) {
        String userId = callUrl(changeTariffModel, userTariffUrl, HttpMethod.PUT).getBody();

        if(userId == null) {
            return null;
        }

        return new ChangeTariffResponseModel(
          Long.parseLong(userId),
          changeTariffModel.getPhoneNumber(),
          changeTariffModel.getTariffId()
        );
    }

    @Override
    public ReportModel generateReport(String phoneNumber) {
        RestTemplate restTemplate = new RestTemplate();
        FindByPhoneModel findByPhoneModel = restTemplate.getForObject(userInfoUrl + phoneNumber, FindByPhoneModel.class);

        if(findByPhoneModel == null) {
            return null;
        }

        UserCallsModel userCallsModel = restTemplate.getForObject(brtCallsUrl + findByPhoneModel.getUserId(), UserCallsModel.class);

         return new ReportModel(
          findByPhoneModel.getUserId(),
          phoneNumber,
          findByPhoneModel.getTariffId(),
          userCallsModel.getAccountCallList(),
          userCallsModel.getTotalAmount(),
          null
        );
    }


    @Override
    public CreateCustomerModel createCustomer(CreateCustomerModel createCustomerModel) {
        CreateProfileModel createProfileModel = new CreateProfileModel(
          createCustomerModel.getPhoneNumber(),
          createCustomerModel.getPassword(),
          "USER",
          createCustomerModel.getTariffId()
        );

        String userId = callUrl(createProfileModel, saveUserUrl, HttpMethod.POST).getBody();

        if(userId == null) {
            return null;
        }

        CreateAccountRequestModel createAccountRequestModel = new CreateAccountRequestModel(
          Long.parseLong(userId),
          createCustomerModel.getBalance()
        );

        callUrl(createAccountRequestModel, createAccountUrl, HttpMethod.POST);

        return createCustomerModel;
    }













    private ResponseEntity<String> callUrl(Object obj, String url, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson;

        try {
            requestJson = objectMapper.writeValueAsString(obj);
        }
        catch(JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        return restTemplate.exchange(url, httpMethod, entity, String.class);
    }
}
