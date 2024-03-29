package com.tigd.api.exceptions;

import java.util.NoSuchElementException;

/**
 * @author viniciuslemes10, gemeoslemes<br>
 * Exceção lançada quando empresa não pode ser encontrada.
 **/
public class EmpresaNotFoundException extends NoSuchElementException {
    /**
     * Construtor padrão para criar a instância EmpresaNotFoundException
     * com a menssagem "Empresa não encontrada!".
     **/
    public EmpresaNotFoundException() {
        super("Empresa não encontrada!");
    }

    /**
     * Construtor que cria a instância EmpresaNotFoundException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public EmpresaNotFoundException(String message) {
        super(message);
    }
}
