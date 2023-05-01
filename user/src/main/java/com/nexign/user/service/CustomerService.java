package com.nexign.user.service;

import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.CreateProfileModel;
import com.nexign.common.model.FindByPhoneModel;
import com.nexign.common.model.UserPhoneNumberModel;
import com.nexign.user.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    FindByPhoneModel findByPhoneNumber(String phone) throws CustomerNotFoundException;
    long saveCustomer(CreateProfileModel profileModel);
    long changeTariff(ChangeTariffModel changeTariffModel);
    List<UserPhoneNumberModel> getPhoneNumbers(List<Long> idList);
}
