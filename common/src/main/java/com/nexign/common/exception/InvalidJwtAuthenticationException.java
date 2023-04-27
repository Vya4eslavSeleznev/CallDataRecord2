package com.nexign.common.exception;

public class InvalidJwtAuthenticationException extends Exception {

    public InvalidJwtAuthenticationException() {
        super("Expired or invalid token");
    }
}
