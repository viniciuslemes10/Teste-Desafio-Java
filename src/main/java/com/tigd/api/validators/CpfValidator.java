package com.tigd.api.validators;

import com.tigd.api.exceptions.CpfIllegalArgException;
import org.springframework.stereotype.Component;

@Component
public class CpfValidator {
    protected boolean validarDigitoVerificadorCpf(String typeDocument, int indiceSoma, int indiceDigito) {
        int sum = 0;
        sum = calcularSomaPonderada(typeDocument, sum, indiceSoma, indiceDigito);
        return validarDigitoVerificadorCpfPorIndice(sum, typeDocument, indiceSoma);
    }

    protected boolean validarDacCpf(boolean dacOne, boolean dacTwo) {
        if(dacOne && dacTwo) {
            return true;
        } else {
            throw new CpfIllegalArgException();
        }
    }

    protected void isDigitosIguais(String document) {
        if (areTheSameDigits(document)) {
            throw new CpfIllegalArgException();
        }
    }

    private boolean validarDigitoVerificadorCpfPorIndice(int sum, String typeDocument, int posicaoDigitoValidacao) {
        int digitoCalculado = calcularDigitoVerificador(sum);
        return Character.getNumericValue(typeDocument.charAt(posicaoDigitoValidacao)) == digitoCalculado;
    }

    private int calcularDigitoVerificador(int sum) {
        int resto = 11 - (sum % 11);
        if(resto > 9) {
            return 0;
        }
        return resto;
    }

    private int calcularSomaPonderada(String typeDocument, int sum, int tamanhoDoFor, int indice) {
        for(int i = 0; i < tamanhoDoFor; i++) {
            sum += Character.getNumericValue(typeDocument.charAt(i)) * (indice-i);
        }
        return sum;
    }

    private boolean areTheSameDigits(String typeDocument) {
        char primeiroDigito = typeDocument.charAt(0);
        return percorrendoDocumento(typeDocument, primeiroDigito);
    }

    private boolean percorrendoDocumento(String document, char digito) {
        for (int i = 1; i < document.length(); i++) {
            if (document.charAt(i) != digito) {
                return false;
            }
        }
        return true;
    }

}