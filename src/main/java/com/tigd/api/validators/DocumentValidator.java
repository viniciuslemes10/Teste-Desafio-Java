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
        if((contexto.equals("cliente") && isCPF(typeDocument)) || (contexto.equals("empresa") && isCNPJ(typeDocument))) {
            return typeDocument;
        }
        return typeDocument;
    }

    private boolean isCNPJ(String document) {
        return document.length() == 14 && validarCnpj(document);
    }

    private boolean isCPF(String document) {
        return document.length() == 11 && validarCpf(document);
    }

    private boolean validarCnpj(String typeDocument) {
        cnpjValidator.isDigitosIguais(typeDocument);
        boolean primeiroDigito = cnpjValidator.validarCNPJPrimeiroDigito(typeDocument);
        boolean segundoDigito = cnpjValidator.validarCNPJSegundoDigito(typeDocument);
        return cnpjValidator.confirmarCnpjDacs(primeiroDigito, segundoDigito);
    }

    private boolean validarCpf(String typeDocument) {
        cpfValidator.isDigitosIguais(typeDocument);
        boolean primeiroDigito = cpfValidator.validarDigitoVerificadorCpf(typeDocument, 9, 10);
        boolean segundoDigito = cpfValidator.validarDigitoVerificadorCpf(typeDocument, 10, 11);
        return cpfValidator.validarDacCpf(primeiroDigito, segundoDigito);
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
