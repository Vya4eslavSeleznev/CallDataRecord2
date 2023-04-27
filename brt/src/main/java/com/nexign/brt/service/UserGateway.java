package com.nexign.brt.service;

import com.nexign.common.model.FindByPhoneModel;

public interface UserGateway {

    FindByPhoneModel getUserInfo(String phoneNumber);
}
