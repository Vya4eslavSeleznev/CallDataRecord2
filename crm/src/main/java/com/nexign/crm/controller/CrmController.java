package com.nexign.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexign.common.model.AuthRequestModel;
import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.PaymentModel;
import com.nexign.common.model.UserCredentialModel;
import com.nexign.crm.exception.PaymentLessThanZeroException;
import com.nexign.crm.model.*;
import com.nexign.crm.service.CrmService;
import com.nexign.crm.service.SignInService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PaymentResponseModel> payment(@RequestBody PaymentModel paymentModel) {
        try {
            return new ResponseEntity<>(crmService.callBrtPayment(paymentModel), HttpStatus.OK);
        }
        catch(PaymentLessThanZeroException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/abonent/report/{phoneNumber}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReportModel> customerReport(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(crmService.generateReport(phoneNumber), HttpStatus.OK);
    }

    @PatchMapping("/manager/billing")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BillingModel> billing() {
        return new ResponseEntity<>(crmService.runBilling(), HttpStatus.OK);
    }

    @PostMapping("/manager/abonent")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CreateCustomerResponse> createUser(@RequestBody CreateCustomerModel createCustomerModel) {
        return new ResponseEntity<>(crmService.createCustomer(createCustomerModel), HttpStatus.OK);
    }

    @PatchMapping("manager/changeTariff")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ChangeTariffResponseModel> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(crmService.changeTariff(changeTariffModel), HttpStatus.OK);
    }

    @PostMapping("/manager/profile")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createManager(@RequestBody UserCredentialModel userCredentialModel) {
        crmService.createManager(userCredentialModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
