package com.nexign.crm.service;

import com.nexign.crm.model.PaymentModel;
import com.nexign.crm.model.PaymentResponseModel;
import com.nexign.crm.model.ReportModel;
import org.springframework.http.ResponseEntity;

public interface CrmService {

    PaymentResponseModel callBrtPayment(PaymentModel paymentModel);
    ResponseEntity<?> callUserTariff(Object obj);
    public ReportModel generateReport(String phoneNumber);
}
