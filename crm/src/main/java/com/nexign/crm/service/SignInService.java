package com.nexign.crm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexign.common.model.AuthRequestModel;
import com.nexign.crm.model.TokenResponseModel;

public interface SignInService {

    TokenResponseModel signIn(AuthRequestModel authRequestModel) throws JsonProcessingException;
}
