package com.nexign.brt.exception;

public class BalanceLessThanZeroException extends Exception {

    public BalanceLessThanZeroException() {
        super("Balance less than zero");
    }
}
