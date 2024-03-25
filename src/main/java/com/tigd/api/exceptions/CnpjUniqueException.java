package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;



public class CnpjUniqueException extends DataIntegrityViolationException {

    public CnpjUniqueException() {
        super("CNPJ já cadastrado!");
    }

    public CnpjUniqueException(String message) {
        super(message);
    }
}
