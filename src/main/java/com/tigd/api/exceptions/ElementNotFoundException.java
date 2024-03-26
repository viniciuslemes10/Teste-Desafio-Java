package com.tigd.api.exceptions;

import java.util.NoSuchElementException;

public class ElementNotFoundException extends NoSuchElementException {

    public ElementNotFoundException() {
        super("Cliente ou Empresa não encontrado!");
    }
    public ElementNotFoundException(String message) {
        super(message);
    }
}
