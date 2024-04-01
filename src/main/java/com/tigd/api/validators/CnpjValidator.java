package com.tigd.api.validators;

import com.tigd.api.exceptions.CnpjIllegalArgException;
import org.springframework.stereotype.Component;

/**
 * @author viniciuslemes10, gemeoslemes<br>
 * Classe de validação de CNPJ(Cadastro Nacional de Pessoa Júridica).
 * Estende da classe {@link CpfValidator} para reutilizar funcionalidades de validação.
 **/
@Component
public class CnpjValidator extends CpfValidator {

    /**
     * Valida o segundo digito verificador de um CNPJ.
     * @param documentCnpj O CNPJ a ser validado, com uma sequência de número.<br>
     * {@link #calcularSomaCNPJ} passando o documento(CNPJ) e o índice que será verificado.
     * @return {@link #compararDigitoVerificador} comparando o resultado do método {@link #calcularSomaCNPJ},
     * e a posição do último digito verificador(13ª) e o cnpj passado.
     **/
    protected boolean validarCNPJSegundoDigito(String documentCnpj) {
        int result = calcularSomaCNPJ(documentCnpj, 12);
        return compararDigitoVerificador(result, 13, documentCnpj);
    }

    /**
     * Valida o primeiro digito verificador de um CNPJ.
     * @param documentCnpj O CNPJ a ser validado, com uma sequência de número.<br>
     * {@link #calcularSomaCNPJ} passando o documento(CNPJ) e o índice que será verificado.
     * @return {@link #compararDigitoVerificador} comparando o resultado do método {@link #calcularSomaCNPJ},
     * e a posição do último digito verificador(12ª) e o cnpj passado.
     **/
    protected boolean validarCNPJPrimeiroDigito(String documentCnpj) {
        int result = calcularSomaCNPJ(documentCnpj, 11);
        return compararDigitoVerificador(result, 12, documentCnpj);
    }
    /**
     * Calcula a soma ponderada dos digítos de um CNPJ até determinada posição.
     * @param document O CNPJ a ser utilizado para o cálculo da soma.
     * @param tamanhoDoIndice O índice até o qual os dígitos devem ser considerados na soma.
     * @return A soma ponderada dos digitos do CNPJ até a posição especificada.
     **/
    private int calcularSomaCNPJ(String document, int tamanhoDoIndice) {
        return somarValoresDocumento(document, tamanhoDoIndice);
    }

    /**
     * Soma ponderadamente dos valores dos digítos de um documento até a posição específicada.
     * @param typeDocument O Documento que será validado.
     * @param tamanhoIndice O índice até qual os dígitos devem ser considerados na soma.
     * @return A soma ponderada dos valores dos dígitos do documento até a posição do índice passado como
     * parâmentro.
     **/
    protected int somarValoresDocumento(String typeDocument, int tamanhoIndice) {
        int sum = 0;
        int peso = 2;
        for (int i = tamanhoIndice; i >= 0; i--) {
            sum += Character.getNumericValue(typeDocument.charAt(i)) * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        return sum;
    }

    /**
     * Compara o dígito verificador calculado com o dígito verificador fornecido em uma posição específica do documento.
     *
     * @param resultadoDaSoma O resultado da soma ponderada dos valores dos dígitos do documento.
     * @param posicao A posição do dígito verificador no documento.
     * @param document O documento do qual o dígito verificador será comparado, representado como uma sequência de números.
     * @return true se o dígito verificador calculado for igual ao dígito verificador fornecido, false caso contrário.
     */
    protected boolean compararDigitoVerificador(int resultadoDaSoma, int posicao, String document) {
        int digito = Character.getNumericValue(document.charAt(posicao));
        return calcularDacDeVerificação(resultadoDaSoma) == digito;
    }

    /**
     * Calcula o dígito de verificação de um número, utilizando o algoritmo módulo 11.
     *
     * @param sum A soma ponderada dos valores dos dígitos do documento.
     * @return O dígito de verificação calculado.
     */
    private int calcularDacDeVerificação(int sum) {
        int resto = sum % 11;
        int dac = (resto < 2) ? 0 : (11 - resto);
        return dac;
    }

    /**
     * Confirma a validade dos dígitos de verificação de um CNPJ.
     *
     * @param dacOne O valor booleano que indica se o primeiro dígito de verificação é válido.
     * @param dacTwo O valor booleano que indica se o segundo dígito de verificação é válido.
     * @return true se ambos os dígitos de verificação forem válidos, caso contrário, lança uma exceção.
     * @throws CnpjIllegalArgException Se algum dos dígitos de verificação não for válido.
     */
    public boolean confirmarCnpjDacs(boolean dacOne, boolean dacTwo) {
        if (dacOne && dacTwo) {
            return true;
        } else {
            throw new CnpjIllegalArgException();
        }
    }
}
