package com.nexign.crm.service;

import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.PaymentModel;
import com.nexign.crm.model.*;

public interface CrmService {

    PaymentResponseModel callBrtPayment(PaymentModel paymentModel);
    ChangeTariffResponseModel changeTariff(ChangeTariffModel changeTariffModel);
    ReportModel generateReport(String phoneNumber);
    CreateCustomerResponse createCustomer(CreateCustomerModel createCustomerModel);
    BillingModel runBilling();
}
