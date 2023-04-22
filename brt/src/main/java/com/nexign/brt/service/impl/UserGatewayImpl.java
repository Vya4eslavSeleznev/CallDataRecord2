package com.nexign.brt.service.impl;

import com.nexign.brt.model.FindByPhoneModel;
import com.nexign.brt.service.UserGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserGatewayImpl implements UserGateway {

    private @Value("${user.gateway.url}") String url;

    @Override
    public FindByPhoneModel getUserInfo(String phoneNumber) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url + phoneNumber, FindByPhoneModel.class);
    }
}
