package com.nexign.crm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.crm.service.CallUrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CallUrlServiceImpl implements CallUrlService {

    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<String> callUrl(Object obj, String url, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson;

        try {
            requestJson = objectMapper.writeValueAsString(obj);
        }
        catch(JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        return restTemplate.exchange(url, httpMethod, entity, String.class);
    }
}
