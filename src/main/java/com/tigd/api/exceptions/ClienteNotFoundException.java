package com.tigd.api.exceptions;

import java.util.NoSuchElementException;
/**
 * @author viniciuslemes10, gemeoslemes<br>
 * Exceção lançada quando cliente não pode ser encontrado.
 **/
public class ClienteNotFoundException extends NoSuchElementException {

    /**
     * Construtor padrão de criar a instância de ClienteNotFaundException
     * com a menssagem "Cliente não encontrado!".
     **/
    public ClienteNotFoundException() {
        super("Cliente não encontrado!");
    }

    /**
     * Construtor que cria a instância ClienteNotFoundException
     * com a mensagem personalidaza.
     * @param message A menssagem de erro
     **/
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
