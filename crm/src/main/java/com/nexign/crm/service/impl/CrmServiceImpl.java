package com.nexign.crm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.common.model.*;
import com.nexign.crm.exception.PaymentLessThanZeroException;
import com.nexign.crm.model.*;
import com.nexign.crm.service.CallUrlService;
import com.nexign.crm.service.CrmGateway;
import com.nexign.crm.service.CrmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CrmServiceImpl implements CrmService {

    private CallUrlService callUrlService;
    private CrmGateway crmGateway;
    private ObjectMapper objectMapper;

    private @Value("${brt.payment.url}") String brtPaymentUrl;
    private @Value("${user.tariff.url}") String userTariffUrl;
    private @Value("${user.save.url}") String saveUserUrl;
    private @Value("${brt.account.url}") String createAccountUrl;
    private @Value("${user.phones.url}") String userPhonesUrl;
    private @Value("${manager.save.url}") String saveManagerUrl;

    public CrmServiceImpl(CallUrlService callUrlService, CrmGateway crmGateway, ObjectMapper objectMapper) {
        this.callUrlService = callUrlService;
        this.crmGateway = crmGateway;
        this.objectMapper = objectMapper;
    }

    @Override
    public PaymentResponseModel callBrtPayment(PaymentModel paymentModel) throws PaymentLessThanZeroException {
        String resultId = callUrlService.callUrl(paymentModel, brtPaymentUrl, HttpMethod.PUT).getBody();

        if(resultId == null) {
            return null;
        }

        if(paymentModel.getAmount() < 0) {
            throw new PaymentLessThanZeroException();
        }

        return new PaymentResponseModel(
          Long.parseLong(resultId),
          paymentModel.getPhoneNumber(),
          paymentModel.getAmount()
        );
    }

    @Override
    public ChangeTariffResponseModel changeTariff(ChangeTariffModel changeTariffModel) {
        String userId = callUrlService.callUrl(changeTariffModel, userTariffUrl, HttpMethod.PUT).getBody();

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
        FindByPhoneModel findByPhoneModel = crmGateway.findByPhone(phoneNumber);

        if(findByPhoneModel == null) {
            return null;
        }

        UserCallsModel userCallsModel = crmGateway.getUserCalls(findByPhoneModel);
        String currencyName = crmGateway.getCurrencyNameByTariffId(findByPhoneModel.getTariffId());

         return new ReportModel(
          findByPhoneModel.getUserId(),
          phoneNumber,
          findByPhoneModel.getTariffId(),
          userCallsModel.getAccountCallList(),
          userCallsModel.getTotalAmount(),
           currencyName
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

        String userId = callUrlService.callUrl(createProfileModel, saveUserUrl, HttpMethod.POST).getBody();

        if(userId == null) {
            return null;
        }

        CreateAccountRequestModel createAccountRequestModel = new CreateAccountRequestModel(
          Long.parseLong(userId),
          createCustomerModel.getBalance()
        );

        callUrlService.callUrl(createAccountRequestModel, createAccountUrl, HttpMethod.POST);

        return new CreateCustomerResponse(
          createCustomerModel.getPhoneNumber(),
          createCustomerModel.getTariffId(),
          createCustomerModel.getBalance()
        );
    }

    @Override
    public BillingModel runBilling() {
        UserBalanceModel[] userBalanceModels = crmGateway.getUserBalances();

        if(userBalanceModels == null) {
            return null;
        }

        List<Long> idList = Stream.of(userBalanceModels)
          .map(UserBalanceModel::getUserId)
          .collect(Collectors.toList());

        String phonesStr = callUrlService.callUrl(idList, userPhonesUrl, HttpMethod.POST).getBody();

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

    @Override
    public void createManager(UserCredentialModel userCredentialModel) {
        callUrlService.callUrl(userCredentialModel, saveManagerUrl, HttpMethod.POST);
    }
}
