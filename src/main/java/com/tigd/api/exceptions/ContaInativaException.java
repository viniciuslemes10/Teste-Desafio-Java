package com.tigd.api.exceptions;

public class ContaInativaException extends RuntimeException {

    public ContaInativaException() {
        super("Não é possível fazer alterações de uma conta inativa.");
    }
    public ContaInativaException(String message) {
        super(message);
    }
}
