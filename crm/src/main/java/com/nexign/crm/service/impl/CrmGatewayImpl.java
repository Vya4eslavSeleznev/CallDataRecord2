package com.nexign.crm.service.impl;

import com.nexign.common.model.FindByPhoneModel;
import com.nexign.common.model.UserBalanceModel;
import com.nexign.common.model.UserCallsModel;
import com.nexign.crm.service.CrmGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CrmGatewayImpl implements CrmGateway {

    private @Value("${user.info.url}") String userInfoUrl;
    private @Value("${brt.calls.url}") String brtCallsUrl;
    private @Value("${brt.billing.url}") String billingUrl;
    private @Value("${tariff.currency.url}") String tariffCurrencyUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public FindByPhoneModel findByPhone(String phoneNumber) {
        return restTemplate().getForObject(userInfoUrl + phoneNumber, FindByPhoneModel.class);
    }

    @Override
    public UserCallsModel getUserCalls(FindByPhoneModel findByPhoneModel) {
        return restTemplate().getForObject(brtCallsUrl + findByPhoneModel.getUserId(), UserCallsModel.class);
    }

    @Override
    public UserBalanceModel[] getUserBalances() {
        return restTemplate().getForObject(billingUrl, UserBalanceModel[].class);
    }

    @Override
    public String getCurrencyNameByTariffId(long tariffId) {
        return restTemplate().getForObject(tariffCurrencyUrl + tariffId, String.class);
    }
}
