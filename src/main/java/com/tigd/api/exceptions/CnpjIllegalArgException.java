package com.tigd.api.exceptions;


/**
 * @author viniciuslemes10, gemeoslemes.<br>
 * Exceção lançada quando cnpj é passado de forma irregular ou inválido.
 **/
public class CnpjIllegalArgException extends  IllegalArgumentException{

    /**
     * Construtor padrão de criar a instância CnpjIlegalArgException
     * com a menssagem "CNPJ inválido!".
     **/
    public CnpjIllegalArgException() {
        super("CNPJ inválido!");
    }
    /**
     * Construtor que cria a instância CnpjIlegalArgException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public CnpjIllegalArgException(String message) {
        super(message);
    }
}
