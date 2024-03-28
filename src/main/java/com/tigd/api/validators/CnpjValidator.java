package com.tigd.api.validators;

import com.tigd.api.exceptions.CnpjIllegalArgException;
import org.springframework.stereotype.Component;

@Component
public class CnpjValidator extends CpfValidator {

    protected boolean validar(String typeDocument, int indice, int posicao) {
        int soma = percorrendoDocumento(typeDocument, indice);
        return validarDigito(soma, posicao, typeDocument);
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

    protected boolean validarDigito(int sum, int posicao, String typeDocument) {
        int digito = Character.getNumericValue(typeDocument.charAt(posicao));
        return calcularDac(sum) == digito;
    }

    private int calcularDac(int sum) {
        int resto = sum % 11;
        int dac = (resto < 2) ? 0 : (11 - resto);
        return dac;
    }

    public boolean validarDacCnpj(boolean dacOne, boolean dacTwo) {
        if (dacOne && dacTwo) {
            return true;
        } else {
            throw new CnpjIllegalArgException();
        }
    }
}
