package com.nexign.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.crm.model.PaymentModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/crm")
public class CrmController {

    private ObjectMapper objectMapper;
    private @Value("${brt.payment.url}") String url;

    public CrmController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PatchMapping("/abonent/pay/")
    public ResponseEntity<?> payment(@RequestBody PaymentModel paymentModel) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson;

        try {
            requestJson = objectMapper.writeValueAsString(paymentModel);
        }
        catch(JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @GetMapping("/abonent/report/{numberPhone}")
    public void customerReport(@PathVariable String phoneNumber) {

    }
}
