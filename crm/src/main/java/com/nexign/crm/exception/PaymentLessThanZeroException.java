package com.nexign.crm.exception;

public class PaymentLessThanZeroException extends Exception {

    public PaymentLessThanZeroException() {
        super("Payment less than zero");
    }
}
