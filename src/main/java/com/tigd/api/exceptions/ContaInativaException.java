package com.tigd.api.exceptions;

/**
 * @author gemeoslemes, viniciuslemes10<br>
 * Exceção lançada quando o cliente ou empresa então inativados na base de dados.
 **/

public class ContaInativaException extends RuntimeException {

    /**
     * Construtor padrão de criar instância  ContaInativaException
     * com a menssagem "Não é possível fazer alterações de uma conta inativa.".
     **/
    public ContaInativaException() {
        super("Não é possível fazer alterações de uma conta inativa.");
    }

    /**
     * Construtor que cria a instância ContaInativaException.
     * com a menssagem personalizada.
     * @param message menssagem de erro
     **/
    public ContaInativaException(String message) {
        super(message);
    }
}
