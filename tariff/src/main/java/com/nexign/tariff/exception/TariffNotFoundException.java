package com.nexign.tariff.exception;

public class TariffNotFoundException extends Exception {

    public TariffNotFoundException() {
        super("Tariff was not found");
    }
}
