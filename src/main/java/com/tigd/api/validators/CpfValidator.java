package com.tigd.api.validators;

import com.tigd.api.exceptions.CpfIllegalArgException;
import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    /**
     * Valida um dígito verificador de CPF em um documento, dado os índices da soma ponderada e do dígito.
     *
     * @param typeDocument O CPF a ser validado, representado como uma sequência de números.
     * @param indiceSoma O índice inicial para a soma ponderada dos dígitos.
     * @param indiceDigito O índice do dígito verificador no documento.
     * @return true se o dígito verificador do CPF for válido, false caso contrário.<br>
     * {@link #calcularSomaPonderada(String, int, int, int)}
     * {@link #validarDigitoVerificadorCpfPorIndice(int, String, int)}
     */
    protected boolean validarDigitoVerificadorCpf(String typeDocument, int indiceSoma, int indiceDigito) {
        int sum = 0;
        sum = calcularSomaPonderada(typeDocument, sum, indiceSoma, indiceDigito);
        return validarDigitoVerificadorCpfPorIndice(sum, typeDocument, indiceSoma);
    }

    /**
     * Valida os dígitos verificadores de um CPF.
     *
     * @param dacOne O valor booleano que indica se o primeiro dígito verificador é válido.
     * @param dacTwo O valor booleano que indica se o segundo dígito verificador é válido.
     * @return true se ambos os dígitos verificadores forem válidos; lança uma exceção CpfIllegalArgException caso contrário.
     * @throws CpfIllegalArgException se algum dos dígitos verificadores não for válido. Mostrando a menssagem e o status.
     */
    protected boolean validarDacCpf(boolean dacOne, boolean dacTwo) {
        if(dacOne && dacTwo) {
            return true;
        } else {
            throw new CpfIllegalArgException();
        }
    }
    /**
     * Verifica se todos os dígitos de um CPF são iguais.
     *
     * @param document O CPF a ser verificado, representado como uma sequência de números.
     * @throws CpfIllegalArgException Se todos os dígitos do CPF forem iguais.
     */
    protected void isDigitosIguais(String document) {
        if (areTheSameDigits(document)) {
            throw new CpfIllegalArgException();
        }
    }

    /**
     * Valida um dígito verificador de CPF em uma posição específica do documento, dado o resultado da soma ponderada.
     *
     * @param sum A soma ponderada dos valores dos dígitos do documento.
     * @param typeDocument O CPF a ser validado, representado como uma sequência de números.
     * @param posicaoDigitoValidacao A posição do dígito verificador no documento.
     * @return true se o dígito verificador do CPF na posição especificada for válido, false caso contrário.
     */
    private boolean validarDigitoVerificadorCpfPorIndice(int sum, String typeDocument, int posicaoDigitoValidacao) {
        int digitoCalculado = calcularDigitoVerificador(sum);
        return Character.getNumericValue(typeDocument.charAt(posicaoDigitoValidacao)) == digitoCalculado;
    }

    /**
     * Calcula o dígito verificador de um número utilizando o algoritmo módulo 11.
     *
     * @param sum A soma ponderada dos valores dos dígitos do documento.
     * @return O dígito verificador calculado.
     */
    private int calcularDigitoVerificador(int sum) {
        int resto = 11 - (sum % 11);
        if(resto > 9) {
            return 0;
        }
        return resto;
    }

    /**
     * Calcula a soma ponderada dos valores dos dígitos de um documento até uma determinada posição.
     *
     * @param typeDocument O documento do qual os valores dos dígitos serão somados, representado como uma sequência de números.
     * @param sum A soma parcial a ser acumulada durante o cálculo.
     * @param tamanhoDoFor O tamanho do loop for, determinando quantos dígitos do documento serão considerados na soma.
     * @param indice O índice do dígito verificador no documento.
     * @return A soma ponderada dos valores dos dígitos do documento até a posição especificada.
     */
    private int calcularSomaPonderada(String typeDocument, int sum, int tamanhoDoFor, int indice) {
        for(int i = 0; i < tamanhoDoFor; i++) {
            sum += Character.getNumericValue(typeDocument.charAt(i)) * (indice-i);
        }
        return sum;
    }

    /**
     * Verifica se todos os dígitos de um documento são iguais.
     *
     * @param typeDocument O documento a ser verificado, representado como uma sequência de caracteres.
     * @return true se todos os dígitos do documento forem iguais; false caso contrário.
     * @see #percorrendoDocumento(String, char)
     */
    private boolean areTheSameDigits(String typeDocument) {
        char primeiroDigito = typeDocument.charAt(0);
        return percorrendoDocumento(typeDocument, primeiroDigito);
    }

    /**
     * Verifica se todos os dígitos de um documento são iguais ao primeiro dígito especificado.
     *
     * @param document O documento a ser verificado, representado como uma sequência de caracteres.
     * @param digito O dígito de referência para comparação.
     * @return true se todos os dígitos do documento forem iguais ao dígito de referência; false caso contrário.
     */
    private boolean percorrendoDocumento(String document, char digito) {
        for (int i = 1; i < document.length(); i++) {
            if (document.charAt(i) != digito) {
                return false;
            }
        }
        return true;
    }

}