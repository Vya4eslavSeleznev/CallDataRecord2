package com.nexign.crm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.common.model.AuthRequestModel;
import com.nexign.common.model.CallCostCalculatedEvent;
import com.nexign.crm.model.TokenResponseModel;
import com.nexign.crm.service.CallUrlService;
import com.nexign.crm.service.SignInService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private CallUrlService callUrlService;
    private ObjectMapper objectMapper;
    private @Value("${user.signin.url}") String userSignInUrl;

    public SignInServiceImpl(CallUrlService callUrlService, ObjectMapper objectMapper) {
        this.callUrlService = callUrlService;
        this.objectMapper = objectMapper;
    }

    @Override
    public TokenResponseModel signIn(AuthRequestModel authRequestModel) throws JsonProcessingException {
        String token =  callUrlService.callUrl(authRequestModel, userSignInUrl, HttpMethod.POST).getBody();

        if(token == null) {
            return null;
        }

        return objectMapper.readValue(token, TokenResponseModel.class);
    }
}
