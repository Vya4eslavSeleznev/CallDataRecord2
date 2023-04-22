package com.nexign.user.controller;

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
        return new ResponseEntity<>(customerService.findByPhoneNumber(phone), HttpStatus.OK);
    }

    @PostMapping
    public void createProfile(@RequestBody CreateProfileModel profileModel) {
        customerService.saveCustomer(profileModel);
    }
}
