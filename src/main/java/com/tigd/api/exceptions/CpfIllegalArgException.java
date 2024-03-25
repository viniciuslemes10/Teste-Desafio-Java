package com.tigd.api.exceptions;

public class CpfIllegalArgException extends IllegalArgumentException{

    public CpfIllegalArgException() {
        super("CPF inválido!");
    }

    public CpfIllegalArgException(String message) {
        super(message);
    }
}
