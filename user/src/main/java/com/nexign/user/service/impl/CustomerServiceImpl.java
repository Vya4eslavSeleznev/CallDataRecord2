package com.nexign.user.service.impl;

import com.nexign.user.entity.Customer;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.ChangeTariffModel;
import com.nexign.user.model.CreateProfileModel;
import com.nexign.user.model.FindByPhoneModel;
import com.nexign.user.model.UserPhoneNumberModel;
import com.nexign.user.repository.CustomerRepository;
import com.nexign.user.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public FindByPhoneModel findByPhoneNumber(String phone) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByPhoneNumber(phone);

        if(customer == null) {
            throw new CustomerNotFoundException();
        }

        return new FindByPhoneModel(customer.getUserCredential().getId(), customer.getTariffId());
    }

    @Override
    public long saveCustomer(CreateProfileModel profileModel) {
        UserCredential user = new UserCredential(
          profileModel.getRole(),
          passwordEncoder.encode(profileModel.getPassword()),
          profileModel.getUsername()
        );

        Customer customer = new Customer(user, profileModel.getPhoneNumber(), profileModel.getTariffId());
        customerRepository.save(customer);
        return user.getId();
    }

    @Override
    public long changeTariff(ChangeTariffModel changeTariffModel) {
        Customer customer = customerRepository.findByPhoneNumber(changeTariffModel.getPhoneNumber());
        customer.setTariffId(changeTariffModel.getTariffId());
        return customerRepository.save(customer).getId();
    }

    @Override
    public List<UserPhoneNumberModel> getPhoneNumbers(List<Long> idList) {
        List<Customer> customers = customerRepository.findByUserIds(idList);

        return customers
          .stream()
          .map(c -> new UserPhoneNumberModel(c.getUserCredential().getId(), c.getPhoneNumber()))
          .collect(Collectors.toList());
    }
}
