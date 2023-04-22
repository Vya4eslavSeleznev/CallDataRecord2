package com.nexign.brt.service;

import com.nexign.brt.model.FindByPhoneModel;

public interface UserGateway {

    FindByPhoneModel getUserInfo(String phoneNumber);
}
