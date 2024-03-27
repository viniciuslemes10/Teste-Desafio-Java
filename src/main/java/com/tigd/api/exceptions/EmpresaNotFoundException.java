package com.tigd.api.exceptions;

import java.util.NoSuchElementException;

public class EmpresaNotFoundException extends NoSuchElementException {
    public EmpresaNotFoundException() {
        super("Empresa não encontrada!");
    }

    public EmpresaNotFoundException(String message) {
        super(message);
    }
}
