package com.tigd.api.validators;

import org.springframework.stereotype.Component;

@Component
public class CnpjValidator {
    protected boolean validarDigito(int dac, int posicao, String typeDocment) {
        int digito = Character.getNumericValue(typeDocment.charAt(posicao));
        if(dac == digito) {
            return true;
        }
        return false;
    }


    protected int calcularDac(int sum) {
        int resto = sum % 11;
        int dac = (resto < 2) ? 0 : (11 - resto);
        return dac;
    }

    protected int percorrendoDocumento(String typeDocument, int sum, int peso, int tamanhoIndice) {
        for(int i = tamanhoIndice; i >= 0; i--) {
            int num = Character.getNumericValue(typeDocument.charAt(i));
            sum += num * peso;
            peso++;
            if(peso == 10) {
                peso = 2;
            }
        }
        return sum;
    }

}
