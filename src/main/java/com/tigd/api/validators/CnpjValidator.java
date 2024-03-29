package com.tigd.api.validators;

import com.tigd.api.exceptions.CnpjIllegalArgException;
import org.springframework.stereotype.Component;

@Component
public class CnpjValidator extends CpfValidator {

    protected boolean validarCNPJSegundoDigito(String typeDocument) {
        int result = calcularSomaCNPJ(typeDocument, 12);
        return compararDigitoVerificador(result, 13, typeDocument);
    }
    protected boolean validarCNPJPrimeiroDigito(String typeDocument) {
        int soma = calcularSomaCNPJ(typeDocument, 11);
        return compararDigitoVerificador(soma, 12, typeDocument);
    }

    private int calcularSomaCNPJ(String document, int tamanhoDoIndice) {
        return percorrendoDocumento(document, tamanhoDoIndice);
    }

    protected int percorrendoDocumento(String typeDocument, int tamanhoIndice) {
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

    protected boolean compararDigitoVerificador(int resultadoDaSoma, int posicao, String document) {
        int digito = Character.getNumericValue(document.charAt(posicao));
        return calcularDacDeVerificação(resultadoDaSoma) == digito;
    }

    private int calcularDacDeVerificação(int sum) {
        int resto = sum % 11;
        int dac = (resto < 2) ? 0 : (11 - resto);
        return dac;
    }

    public boolean confirmarCnpjDacs(boolean dacOne, boolean dacTwo) {
        if (dacOne && dacTwo) {
            return true;
        } else {
            throw new CnpjIllegalArgException();
        }
    }
}
