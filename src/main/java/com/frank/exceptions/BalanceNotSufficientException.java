package com.frank.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String s) {
        super(s);
    }
}
