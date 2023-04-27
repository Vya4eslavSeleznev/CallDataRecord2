package com.nexign.crm.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface CallUrlService {

    ResponseEntity<String> callUrl(Object obj, String url, HttpMethod httpMethod);
}
