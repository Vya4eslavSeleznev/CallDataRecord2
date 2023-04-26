package com.nexign.user.exception;

public class InvalidJwtAuthenticationException extends Exception {

    public InvalidJwtAuthenticationException() {
        super("Expired or invalid token");
    }
}
