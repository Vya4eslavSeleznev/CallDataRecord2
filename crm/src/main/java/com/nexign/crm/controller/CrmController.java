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
    public void billing() {

    }

    @PostMapping("/manager/abonent")
    public void createUser(@RequestBody CreateProfileModel createProfileModel) {
    }

    @PatchMapping("manager/changeTariff")
    public ResponseEntity<ChangeTariffResponseModel> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
        return new ResponseEntity<>(crmService.changeTariff(changeTariffModel), HttpStatus.OK);
    }

}
