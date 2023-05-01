package com.nexign.crm.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super("Unauthorized user");
    }
}
