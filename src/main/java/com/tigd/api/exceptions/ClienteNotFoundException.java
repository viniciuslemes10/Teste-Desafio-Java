package com.tigd.api.exceptions;

import java.util.NoSuchElementException;

public class ClienteNotFoundException extends NoSuchElementException {
    public ClienteNotFoundException() {
        super("Cliente não encontrado!");
    }
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
