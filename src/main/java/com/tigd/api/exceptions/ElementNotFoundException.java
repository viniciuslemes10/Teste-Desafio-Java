package com.tigd.api.exceptions;

import java.util.NoSuchElementException;

/**
 * @author gemeoslemes, vinicuslemes10<br>
 * Exceção lançada quando cliente ou empresa não são encontrado na base de dados.
 **/
public class ElementNotFoundException extends NoSuchElementException {
    /**
     * Construtor padrão para criar instância ElementNotFoundException
     * com a menssagem "Cliente ou Empresa nõo encontrado!".
     **/
    public ElementNotFoundException() {
        super("Cliente ou Empresa não encontrado!");
    }

    /**
     * Construtor cria a instância ElementNotFoundException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public ElementNotFoundException(String message) {
        super(message);
    }
}
