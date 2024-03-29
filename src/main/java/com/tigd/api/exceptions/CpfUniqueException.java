package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author gemeoslemes, viniciuslemes10<br>
 * Exceção lançada quando cpf já cadastrado na base de dados.
 **/
public class CpfUniqueException extends DataIntegrityViolationException {

    /**
     * Construtor padrão de criar instância CpfUiqueException
     * com a menssagem "CPF inválido!".
     **/
    public CpfUniqueException() {
        super("CPF já cadastrado!");
    }
    /**
     * Construtor que cria a instância CpfUniqueException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public CpfUniqueException(String message) {
        super(message);
    }
}
