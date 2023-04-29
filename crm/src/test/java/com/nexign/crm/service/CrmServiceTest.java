package com.nexign.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.common.model.PaymentModel;
import com.nexign.crm.model.PaymentResponseModel;
import com.nexign.crm.service.impl.CrmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrmServiceTest {

    @InjectMocks
    private CrmServiceImpl crmService;

    @Mock
    private CallUrlService callUrlService;

    @Mock
    private CrmGateway crmGateway;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        crmService = new CrmServiceImpl(callUrlService, crmGateway, objectMapper);
    }

    @Test
    public void call_brt_payment() {
        String phone = "89997776655";
        double amount = 100;

        PaymentModel paymentModel = new PaymentModel(phone, amount);
        String g = "";

        when(callUrlService.callUrl(paymentModel, null, HttpMethod.PUT)).thenReturn(
          new ResponseEntity<>("8", HttpStatus.OK));

        PaymentResponseModel expectedResponseModel = new PaymentResponseModel(1L, phone, amount);

        PaymentResponseModel actualResponseModel = crmService.callBrtPayment(paymentModel);

        verify(callUrlService, times(1)).callUrl(paymentModel, null, HttpMethod.PUT);

        assertEquals(expectedResponseModel.getId(), actualResponseModel.getId());
    }
}
