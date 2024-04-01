package com.tigd.api.exceptions;

/**
 * @author gemeoslemes, viniciuslemes10<br>
 * Exceção lançada quando cpf é passado de forma irregular ou inválido.
 **/
public class CpfIllegalArgException extends IllegalArgumentException{
    /**
     * Construtor padrão de criar a instância CpfIlegalArgException
     * com a menssagem "CPF inválido!".
     **/
    public CpfIllegalArgException() {
        super("CPF inválido!");
    }

    /**
     * Construtor que cria a instância CpfIlegalArgException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public CpfIllegalArgException(String message) {
        super(message);
    }
}
