package com.nexign.brt.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
        super("Account was not found");
    }
}
