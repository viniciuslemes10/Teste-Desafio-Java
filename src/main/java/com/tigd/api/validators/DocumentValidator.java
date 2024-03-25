package com.tigd.api.validators;

import com.tigd.api.exceptions.CnpjIllegalArgException;
import com.tigd.api.exceptions.CpfIllegalArgException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DocumentValidator implements Validator {

    private String documentTruncado;

    @Autowired
    private CpfValidator cpfValidator;

    @Autowired
    private CnpjValidator cnpjValidator;

    @Override
    public String isValid(String document, String contexto) {
        String typeDocument = documentTruncado(document, contexto);
        if(typeDocument.length() == 11 && contexto.equals("cliente")) {
            validarCpf(typeDocument);
            return typeDocument;
        } else if(typeDocument.length() == 14 && contexto.equals("empresa")) {
            validarCnpj(typeDocument);
            return typeDocument;
        }
        return typeDocument;
    }

    private boolean validarCnpj(String typeDocument) {
        boolean digitosIguais = true;
        digitosIguais = cpfValidator.isDigitosIguais(typeDocument, digitosIguais);

        if(digitosIguais) {
            throw new CnpjIllegalArgException();
        }

        int sum = 0;
        int peso = 2;
        sum = cnpjValidator.percorrendoDocumento(typeDocument, sum, peso, 11);

        int dac1 = cnpjValidator.calcularDac(sum);

        sum = 0;
        peso = 2;
        sum = cnpjValidator.percorrendoDocumento(typeDocument, sum, peso, 12);
        int dac2 = cnpjValidator.calcularDac(sum);

        boolean primeiroDigito = cnpjValidator.validarDigito(dac1, 12, typeDocument);
        boolean segundoDigito = cnpjValidator.validarDigito(dac2, 13, typeDocument);

        if(primeiroDigito && segundoDigito) {
            return true;
        } else {
            throw new CnpjIllegalArgException();
        }
    }

    private boolean validarCpf(String typeDocument) {
        boolean digitosIguais = true;
        digitosIguais = cpfValidator.isDigitosIguais(typeDocument, digitosIguais);

        if(digitosIguais) {
            throw new CpfIllegalArgException();
        }

        int sum = 0;
        sum = cpfValidator.percorrendoDocumento(typeDocument, sum, 9, 10);
        boolean primeiroDigito = cpfValidator.validarDigitos(sum, typeDocument, 9);

        sum = 0;
        sum = cpfValidator.percorrendoDocumento(typeDocument, sum, 10, 11);
        boolean segundoDigito = cpfValidator.validarDigitos(sum, typeDocument, 10);

        if(primeiroDigito && segundoDigito) {
            return true;
        } else {
            throw new CpfIllegalArgException();
        }
    }

    private String documentTruncado(String document, String contexto) {
        documentTruncado = lengthDoc(document);
        if(documentTruncado.length() == 11 || documentTruncado.length() == 14) {
            return documentTruncado;
        } else {
            if(contexto.equals("cliente")) {
                throw new CpfIllegalArgException();
            } else if (contexto.equals("empresa")) {
                throw new CnpjIllegalArgException();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    private String lengthDoc(String document) {
        if (document.length() == 14) {
            documentTruncado = document.replaceAll("[.-]", "");
            return documentTruncado;
        } else if(document.length() == 18) {
            documentTruncado = document.replaceAll("[./-]", "");
            return documentTruncado;
        }
        return document;
    }

}
