package com.nexign.user.controller;

import com.nexign.user.entity.Customer;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.ChangeTariffModel;
import com.nexign.user.model.CreateProfileModel;
import com.nexign.user.model.FindByPhoneModel;
import com.nexign.user.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/{phone}")
    public ResponseEntity<FindByPhoneModel> findCustomerByPhone(@PathVariable String phone) {

        try {
            FindByPhoneModel findByPhoneModel = customerService.findByPhoneNumber(phone);
            return new ResponseEntity<>(findByPhoneModel, HttpStatus.OK);
        }
        catch(CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
}

    @PostMapping
    public ResponseEntity<Customer> createProfile(@RequestBody CreateProfileModel profileModel) {
        return new ResponseEntity<>(customerService.saveCustomer(profileModel), HttpStatus.OK);
    }

    @PutMapping("/tariff")
    public ResponseEntity<Long> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(customerService.changeTariff(changeTariffModel), HttpStatus.OK);
    }
}
