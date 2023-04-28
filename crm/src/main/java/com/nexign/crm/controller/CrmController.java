package com.nexign.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexign.common.model.AuthRequestModel;
import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.PaymentModel;
import com.nexign.common.model.UserCredentialModel;
import com.nexign.crm.model.*;
import com.nexign.crm.service.CrmService;
import com.nexign.crm.service.SignInService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crm")
@AllArgsConstructor
public class CrmController {

    private CrmService crmService;
    private SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseModel> signIn(@RequestBody AuthRequestModel authRequestModel) {
        try {
            return new ResponseEntity<>(signInService.signIn(authRequestModel), HttpStatus.OK);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/abonent/pay/")
    public ResponseEntity<PaymentResponseModel> payment(@RequestBody PaymentModel paymentModel) {
        return new ResponseEntity<>(crmService.callBrtPayment(paymentModel), HttpStatus.OK);
    }

    @GetMapping("/abonent/report/{phoneNumber}")
    public ResponseEntity<ReportModel> customerReport(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(crmService.generateReport(phoneNumber), HttpStatus.OK);
    }

    @PatchMapping("/manager/billing")
    public ResponseEntity<BillingModel> billing() {
        return new ResponseEntity<>(crmService.runBilling(), HttpStatus.OK);
    }

    @PostMapping("/manager/abonent")
    public ResponseEntity<CreateCustomerResponse> createUser(@RequestBody CreateCustomerModel createCustomerModel) {
        return new ResponseEntity<>(crmService.createCustomer(createCustomerModel), HttpStatus.OK);
    }

    @PatchMapping("manager/changeTariff")
    public ResponseEntity<ChangeTariffResponseModel> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(crmService.changeTariff(changeTariffModel), HttpStatus.OK);
    }

    @PostMapping("/manager/profile")
    public ResponseEntity<?> createManager(@RequestBody UserCredentialModel userCredentialModel) {
        crmService.createManager(userCredentialModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
