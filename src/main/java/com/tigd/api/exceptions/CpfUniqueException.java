package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class CpfUniqueException extends DataIntegrityViolationException {
    public CpfUniqueException() {
        super("CPF já cadastrado!");
    }
    public CpfUniqueException(String message) {
        super(message);
    }
}
