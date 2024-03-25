package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailUniqueException extends DataIntegrityViolationException {

    public EmailUniqueException() {
        super("Email já cadastrado!");
    }
    public EmailUniqueException(String message) {
        super(message);
    }
}
