package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author viniciuslemes10, gemeoslemes<br>
 * Exceção lançada quando email passado já está cadastrado em nossa base de dados.
 **/
public class EmailUniqueException extends DataIntegrityViolationException {

    /**
     * Construtor padrão cria a instância EmailUniqueException
     * com a menssagem "Email já cadastrado!".
     **/
    public EmailUniqueException() {
        super("Email já cadastrado!");
    }

    /**
     * Construtor cria a instância EmailUniqueException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public EmailUniqueException(String message) {
        super(message);
    }
}
