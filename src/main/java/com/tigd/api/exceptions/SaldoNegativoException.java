package com.tigd.api.exceptions;

/**
 * @author vinicuslemes10, gemeoslemes
 * Exceção lançada quando saldo do cliente ou empresa é insufiente para realizar a transação.
 **/
public class SaldoNegativoException extends IllegalArgumentException {

    /**
     * Construtor padrão para criar a instância SaldoNegativoException
     * com a menssagem "Saldo insufiente!".
     **/
    public SaldoNegativoException() {
        super("Saldo insuficiente!");
    }

    /**
     * Construtor para criar a instância SaldoNegativoException
     * com a menssagem personalizada.
     * @param message A menssagem de erro
     **/
    public SaldoNegativoException(String message) {
        super(message);
    }
}
