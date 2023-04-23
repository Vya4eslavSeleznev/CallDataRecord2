package com.nexign.user.service.impl;

import com.nexign.user.entity.Customer;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.CreateProfileModel;
import com.nexign.user.model.FindByPhoneModel;
import com.nexign.user.repository.CustomerRepository;
import com.nexign.user.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public FindByPhoneModel findByPhoneNumber(String phone) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByPhoneNumber(phone);

        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        return new FindByPhoneModel(customer.getUserCredential().getId(), customer.getTariffId());
    }

    @Override
    public void saveCustomer(CreateProfileModel profileModel) {
        UserCredential user = new UserCredential(profileModel.getRole(), profileModel.getPassword());
        Customer customer = new Customer(user, profileModel.getPhoneNumber(), profileModel.getTariffId());
        customerRepository.save(customer);
    }

//    @Override
//    public String findPhoneNumberByUserId(long userId) {
//        return null;
//    }
}
