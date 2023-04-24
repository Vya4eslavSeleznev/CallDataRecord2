package com.nexign.user.service;

import com.nexign.user.entity.Customer;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.ChangeTariffModel;
import com.nexign.user.model.CreateProfileModel;
import com.nexign.user.model.FindByPhoneModel;
import com.nexign.user.model.UserPhoneNumberModel;

import java.util.List;

public interface CustomerService {

    FindByPhoneModel findByPhoneNumber(String phone) throws CustomerNotFoundException;
    long saveCustomer(CreateProfileModel profileModel);
    long changeTariff(ChangeTariffModel changeTariffModel);
    List<UserPhoneNumberModel> getPhoneNumbers(List<Long> idList);
}
