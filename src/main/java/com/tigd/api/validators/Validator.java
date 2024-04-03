package com.tigd.api.validators;

/**
 * Interface para validação de documentos.
 *
 * Esta interface define um contrato para implementações de validadores de documentos. Um validador recebe um documento
 * e um contexto como entrada e retorna uma mensagem indicando se o documento é válido ou inválido dentro desse contexto.
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
public interface Validator {
    /**
     * Valida um documento num determinado contexto.
     *
     * @param documento o documento a ser validado
     * @param contexto o contexto no qual o documento está sendo validado
     * @return uma mensagem indicando se o documento é válido ou inválido dentro do contexto fornecido
     */
    String isValid(String documento, String contexto);
}
