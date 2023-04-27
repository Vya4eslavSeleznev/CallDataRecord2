package com.nexign.hrs.service.Impl;

import com.nexign.common.model.CallType;
import com.nexign.common.model.TariffInfoModel;
import com.nexign.hrs.service.TariffGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TariffGatewayImpl implements TariffGateway {

    private @Value("${tariff.gateway.url}") String url;

    @Override
    public List<TariffInfoModel> getTariffInfo(long tariffId, CallType callType) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<TariffInfoModel>> rateResponse =
          restTemplate.exchange(url + tariffId + "/" + callType,
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        return rateResponse.getBody();
    }
}
