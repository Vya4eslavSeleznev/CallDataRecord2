package com.nexign.crm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.common.model.*;
import com.nexign.crm.model.*;
import com.nexign.crm.service.CrmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CrmServiceImpl implements CrmService {

    private ObjectMapper objectMapper;
    private @Value("${brt.payment.url}") String brtPaymentUrl;
    private @Value("${user.tariff.url}") String userTariffUrl;
    private @Value("${user.info.url}") String userInfoUrl;
    private @Value("${brt.calls.url}") String brtCallsUrl;
    private @Value("${user.save.url}") String saveUserUrl;
    private @Value("${brt.account.url}") String createAccountUrl;
    private @Value("${brt.billing.url}") String billingUrl;
    private @Value("${user.phones.url}") String userPhonesUrl;

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
    public CreateCustomerResponse createCustomer(CreateCustomerModel createCustomerModel) {
        CreateProfileModel createProfileModel = new CreateProfileModel(
          createCustomerModel.getPhoneNumber(),
          createCustomerModel.getPassword(),
          createCustomerModel.getUsername(),
          Role.USER,
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

        return new CreateCustomerResponse(
          createCustomerModel.getPhoneNumber(),
          createCustomerModel.getTariffId(),
          createCustomerModel.getBalance()
        );
    }

    @Override
    public BillingModel runBilling() {
        RestTemplate restTemplate = new RestTemplate();
        UserBalanceModel[] userBalanceModels = restTemplate.getForObject(billingUrl, UserBalanceModel[].class);

        if(userBalanceModels == null) {
            return null;
        }

        List<Long> idList = Stream.of(userBalanceModels)
          .map(UserBalanceModel::getUserId)
          .collect(Collectors.toList());

        String phonesStr = callUrl(idList, userPhonesUrl, HttpMethod.POST).getBody();

        if(phonesStr == null) {
            return null;
        }

        List<UserPhoneNumberModel> userPhones = null;

        try {
            userPhones = objectMapper.readValue(phonesStr, new TypeReference<>() {});
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        if(userPhones == null) {
            return null;
        }

        Map<Long, String> phones = userPhones
          .stream()
          .collect(Collectors.toMap(UserPhoneNumberModel::getId, UserPhoneNumberModel::getPhoneNumber));

        List<PhoneAndBalanceModel> phoneAndBalanceList = new ArrayList<>();

        for(UserBalanceModel userBalance : userBalanceModels) {
            long id = userBalance.getUserId();
            phoneAndBalanceList.add(
              new PhoneAndBalanceModel(phones.get(id), userBalance.getBalance())
            );
        }

        return new BillingModel(phoneAndBalanceList);
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
