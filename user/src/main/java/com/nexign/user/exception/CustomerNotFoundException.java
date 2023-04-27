package com.nexign.user.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException() {
        super("Customer was not found");
    }
}
