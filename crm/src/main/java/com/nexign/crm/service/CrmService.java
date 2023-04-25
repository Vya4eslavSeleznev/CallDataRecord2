package com.nexign.crm.service;

import com.nexign.crm.model.*;

public interface CrmService {

    PaymentResponseModel callBrtPayment(PaymentModel paymentModel);
    ChangeTariffResponseModel changeTariff(ChangeTariffModel changeTariffModel);
    ReportModel generateReport(String phoneNumber);
    CreateCustomerModel createCustomer(CreateCustomerModel createCustomerModel);
    BillingModel runBilling();
}
