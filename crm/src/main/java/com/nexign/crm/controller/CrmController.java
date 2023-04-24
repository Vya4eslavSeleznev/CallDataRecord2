package com.nexign.crm.controller;

import com.nexign.crm.model.*;
import com.nexign.crm.service.CrmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crm")
@AllArgsConstructor
public class CrmController {

    private CrmService crmService;

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
    public ResponseEntity<CreateCustomerModel> createUser(@RequestBody CreateCustomerModel createCustomerModel) {
        return new ResponseEntity<>(crmService.createCustomer(createCustomerModel), HttpStatus.OK);
    }

    @PatchMapping("manager/changeTariff")
    public ResponseEntity<ChangeTariffResponseModel> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(crmService.changeTariff(changeTariffModel), HttpStatus.OK);
    }

}