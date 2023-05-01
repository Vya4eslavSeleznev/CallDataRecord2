package com.nexign.user.controller;

import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.CreateProfileModel;
import com.nexign.common.model.FindByPhoneModel;
import com.nexign.common.model.UserPhoneNumberModel;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.LoadUserByUsernameModel;
import com.nexign.user.service.CustomerService;
import com.nexign.user.service.impl.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/{phone}")
    public ResponseEntity<FindByPhoneModel> findCustomerByPhone(@PathVariable String phone) {

        try {
            FindByPhoneModel findByPhoneModel = customerService.findByPhoneNumber(phone);
            return new ResponseEntity<>(findByPhoneModel, HttpStatus.OK);
        }
        catch(CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}

    @PostMapping("/profile")
    public ResponseEntity<Long> createProfile(@RequestBody CreateProfileModel profileModel) {
        return new ResponseEntity<>(customerService.saveCustomer(profileModel), HttpStatus.OK);
    }

    @PutMapping("/tariff")
    public ResponseEntity<Long> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(customerService.changeTariff(changeTariffModel), HttpStatus.OK);
    }

    @PostMapping("/phones")
    public ResponseEntity<List<UserPhoneNumberModel>> getUserPhones(@RequestBody List<Long> userIds) {
        return new ResponseEntity<>(customerService.getPhoneNumbers(userIds), HttpStatus.OK);
    }

    @PostMapping("/username")
    public ResponseEntity<UserDetails> loadUserByUsername(@RequestBody LoadUserByUsernameModel loadUserByUsernameModel) {
        return new ResponseEntity<>(
          customUserDetailsService.loadUserByUsername(loadUserByUsernameModel.getUsername()),
          HttpStatus.OK
        );
    }
}
