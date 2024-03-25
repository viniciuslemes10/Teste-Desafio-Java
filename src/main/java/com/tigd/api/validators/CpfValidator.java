package com.tigd.api.validators;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {
    protected boolean cpfIllegalArgumento(String typeDocument, int posicaoIndice, int digtoverificador) {
        if(Character.getNumericValue(typeDocument.charAt(posicaoIndice)) == digtoverificador) {
            return true;
        } else {
            return false;
        }
    }

    private int resto(int sum) {
        return 11 - (sum % 11);
    }

    protected int percorrendoDocumento(String typeDocument, int sum, int tamanhoDoFor, int posicaoDigito) {
        for(int i = 0; i < tamanhoDoFor; i++) {
            sum += Character.getNumericValue(typeDocument.charAt(i)) * (posicaoDigito-i);
        }
        return sum;
    }

    private int digitoMaiorQueNove(int digitoVericado) {
        if(digitoVericado > 9) {
            digitoVericado = 0;
        }
        return digitoVericado;
    }

    protected boolean isDigitosIguais(String typeDocument, boolean digitosIguais) {
        char primeiroDigito = typeDocument.charAt(0);
        for (int i = 0; i < typeDocument.length(); i++) {
            if(typeDocument.charAt(i) != primeiroDigito) {
                digitosIguais = false;
                break;
            }
        }
        return digitosIguais;
    }

    protected boolean validarDigitos(int sum, String typeDocument, int indice) {
        int digitoVerificado = resto(sum);
        digitoVerificado = digitoMaiorQueNove(digitoVerificado);
        boolean digito = cpfIllegalArgumento(typeDocument, indice, digitoVerificado);
        return digito;
    }
}