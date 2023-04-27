package com.nexign.crm.service.impl;

import com.nexign.common.model.AuthRequestModel;
import com.nexign.crm.service.CallUrlService;
import com.nexign.crm.service.SignInService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private CallUrlService callUrlService;
    private @Value("${user.signin.url}") String userSignInUrl;

    public SignInServiceImpl(CallUrlService callUrlService) {
        this.callUrlService = callUrlService;
    }

    @Override
    public String signIn(AuthRequestModel authRequestModel) {
        return callUrlService.callUrl(authRequestModel, userSignInUrl, HttpMethod.POST).getBody();
    }
}
