package com.nexign.crm.controller;

import com.nexign.crm.model.PaymentModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/crm")
public class CrmController {



//    private @Value("${brt.payment.url}") String url;
//
//    @Override
//    public FindByPhoneModel getUserInfo(String phoneNumber) {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url + phoneNumber, FindByPhoneModel.class);
//    }



    @PatchMapping("/abonent/pay/")
    public void payment(@RequestBody PaymentModel paymentModel) {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url + phoneNumber, FindByPhoneModel.class);
    }
}
