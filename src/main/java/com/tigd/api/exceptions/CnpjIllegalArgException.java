package com.tigd.api.exceptions;

public class CnpjIllegalArgException extends  IllegalArgumentException{
    public CnpjIllegalArgException() {
        super("CNPJ inválido!");
    }
    public CnpjIllegalArgException(String message) {
        super(message);
    }
}
