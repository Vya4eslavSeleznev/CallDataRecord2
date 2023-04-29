package com.nexign.hrs.exception;

public class AboveTariffRateNotFoundException extends Exception {

    public AboveTariffRateNotFoundException() {
        super("Tariff above rate not found");
    }
}
