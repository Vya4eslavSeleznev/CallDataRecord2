package com.nexign.crm.controller;

import com.nexign.crm.model.ChangeTariffModel;
import com.nexign.crm.model.CreateProfileModel;
import com.nexign.crm.model.PaymentModel;
import com.nexign.crm.model.PaymentResponseModel;
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

    @GetMapping("/abonent/report/{numberPhone}")
    public void customerReport(@PathVariable String phoneNumber) {

    }

    @PatchMapping("/manager/billing")
    public void billing() {

    }

    @PostMapping("/manager/abonent")
    public void createUser(@RequestBody CreateProfileModel createProfileModel) {
    }

//    @PatchMapping("manager/changeTariff")
//    public ResponseEntity<?> changeTariff(@RequestBody ChangeTariffModel changeTariffModel) {
//        return crmService.callBrtPayment(changeTariffModel);
//    }

}
