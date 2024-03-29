package com.tigd.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;


/**
 * @author gemeoslemes, viniciuslemes10<br>
 * Exceção lançada quando o CNPJ já está cadastrado na base de dados.
 **/
public class CnpjUniqueException extends DataIntegrityViolationException {

    /**
     * Construtor padrão de criar instância CnpjUniqueException
     * com a menssagem "CNPJ já cadastrado!".
     **/
    public CnpjUniqueException() {
        super("CNPJ já cadastrado!");
    }
    /**
     * Construtor que cria a instância CnpjUniqueException
     * com a menssagem personalizada.
     * @param message menssagem de erro
     **/
    public CnpjUniqueException(String message) {
        super(message);
    }
}
