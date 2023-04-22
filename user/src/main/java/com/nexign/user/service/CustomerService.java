package com.nexign.user.service;

import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.CreateProfileModel;
import com.nexign.user.model.FindByPhoneModel;

public interface CustomerService {

    FindByPhoneModel findByPhoneNumber(String phone) throws CustomerNotFoundException;
    void saveCustomer(CreateProfileModel profileModel);
}
