package com.tigd.api.exceptions;


public class SaldoNegativoException extends IllegalArgumentException {

    public SaldoNegativoException() {
        super("Saldo insuficiente!");
    }

    public SaldoNegativoException(String message) {
        super(message);
    }
}
