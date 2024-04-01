package com.tigd.api.validators;

import com.tigd.api.exceptions.CnpjIllegalArgException;
import com.tigd.api.exceptions.CpfIllegalArgException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author vinicuslemes10, gemeoslemes<br>
 * Esta classe implementa a interface {@link Validator} e fornece funcionalidades para validar documentos.
 *
 * <p>
 * A classe {@code DocumentValidator} é responsável por fornecer métodos para validar diferentes tipos de documentos,
 * como CPF, CNPJ, entre outros. Ela implementa a interface {@link Validator}, que define métodos para validar objetos.
 * </p>
 *
 * <p>
 * Para utilizar esta classe, instancie um objeto {@code DocumentValidator} e utilize os métodos fornecidos para validar documentos.
 * </p>
 *
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>{@code
 * DocumentValidator validator = new DocumentValidator();
 * boolean cpfValido = validator.validarCpf("12345678900");
 * }</pre>
 */
@Component
@Getter
@Setter
public class DocumentValidator implements Validator {

    private String documentTruncado;

    @Autowired
    private CpfValidator cpfValidator;

    @Autowired
    private CnpjValidator cnpjValidator;

    /**
     * Valida um documento de acordo com o contexto especificado e retorna o documento truncado se válido.
     *
     * @param document O documento a ser validado.
     * @param contexto O contexto em que o documento está sendo validado (por exemplo, "cliente" ou "empresa").
     * @return O documento truncado se válido; caso contrário, retorna o documento original.
     * @throws IllegalArgumentException Se o contexto não for "cliente" ou "empresa".
     * @see #documentTruncado(String, String)
     * @see #isCPF(String)
     * @see #isCNPJ(String)
     */
    @Override
    public String isValid(String document, String contexto) {
        String typeDocument = documentTruncado(document, contexto);
        if((contexto.equals("cliente") && isCPF(typeDocument)) || (contexto.equals("empresa") && isCNPJ(typeDocument))) {
            return typeDocument;
        }
        return typeDocument;
    }

    /**
     * Valida o tamanho do CNPJ e se o CNPJ é válido.
     * @param document O documento que será validado.
     * @return se o tamanho for igual a 14 e se o documento é válido.
     **/
    private boolean isCNPJ(String document) {
        return document.length() == 14 && validarCnpj(document);
    }

    /**
     * Valida o tamanho do CPF e se o CPF é válido.
     * @param document O documento que será validado.
     * @return se o tamanho for igual a 11 e se o documento é válido.
     **/
    private boolean isCPF(String document) {
        return document.length() == 11 && validarCpf(document);
    }

    /**
     * Valida um número de CNPJ.
     *
     * <p>
     * Este método utiliza o {@link CnpjValidator} para realizar a validação do CNPJ. Ele verifica se os dígitos do CNPJ não são todos iguais,
     * valida o primeiro e o segundo dígito verificador do CNPJ e, por fim, confirma se ambos os dígitos são válidos.
     * </p>
     *
     * @param typeDocument O número de CNPJ a ser validado.
     * @return true se o CNPJ for válido; false caso contrário.
     * @throws CnpjIllegalArgException Se o CNPJ for inválido ou não estiver no formato correto.
     * @see CnpjValidator
     * @see CnpjValidator#isDigitosIguais(String)
     * @see CnpjValidator#validarCNPJPrimeiroDigito(String)
     * @see CnpjValidator#validarCNPJSegundoDigito(String)
     * @see CnpjValidator#confirmarCnpjDacs(boolean, boolean)
     */
    private boolean validarCnpj(String typeDocument) {
        cnpjValidator.isDigitosIguais(typeDocument);
        boolean primeiroDigito = cnpjValidator.validarCNPJPrimeiroDigito(typeDocument);
        boolean segundoDigito = cnpjValidator.validarCNPJSegundoDigito(typeDocument);
        return cnpjValidator.confirmarCnpjDacs(primeiroDigito, segundoDigito);
    }
    
    /**
     * Valida um número de CPF.
     * 
     * <p>
     * Este método utiliza o {@link CpfValidator} para realizar a validação do CPF. Ele verifica se os dígitos são todos iguais
     * valida o primeiro e o segundo dígito verificador do CPF e, por fim confirma se ambos os dígitos são válidos.
     * </p>
     * 
     * @param typeDocument O número do CPF a ser validado.
     * @return true se o CPF for válido; false caso contrário.
     * @throws CpfIllegalArgException Caso CPF for ilegal ou não estiver na formatação correta.
     * @see CpfValidator
     * @see CpfValidator#validarDigitoVerificadorCpf(String, int, int)
     * @see CpfValidator#validarDacCpf(boolean, boolean)
     **/
    private boolean validarCpf(String typeDocument) {
        cpfValidator.isDigitosIguais(typeDocument);
        boolean primeiroDigito = cpfValidator.validarDigitoVerificadorCpf(typeDocument, 9, 10);
        boolean segundoDigito = cpfValidator.validarDigitoVerificadorCpf(typeDocument, 10, 11);
        return cpfValidator.validarDacCpf(primeiroDigito, segundoDigito);
    }

    /**
     * Trunca um documento removendo caracteres de formatação e verifica se o documento é válido para o contexto especificado.
     *
     * <p>
     * Este método recebe um número de documento e um contexto e trunca o documento removendo caracteres de formatação.
     * Em seguida, verifica se o comprimento do documento truncado é válido para o contexto especificado. Se o contexto for "cliente"
     * e o documento tiver comprimento 11 (CPF), ou se o contexto for "empresa" e o documento tiver comprimento 14 (CNPJ), o documento
     * truncado é retornado. Caso contrário, uma exceção é lançada.
     * </p>
     *
     * @param document O número de documento a ser truncado e validado.
     * @param contexto O contexto em que o documento está sendo validado (por exemplo, "cliente" ou "empresa").
     * @return O documento truncado se for válido para o contexto especificado.
     * @throws CpfIllegalArgException Se o contexto for "cliente" e o documento não for um CPF válido.
     * @throws CnpjIllegalArgException Se o contexto for "empresa" e o documento não for um CNPJ válido.
     * @throws IllegalArgumentException Se o contexto não for reconhecido.
     * @see #lengthDoc(String)
     */
    private String documentTruncado(String document, String contexto) {
        documentTruncado = lengthDoc(document);
        if(documentTruncado.length() == 11 || documentTruncado.length() == 14) {
            return documentTruncado;
        } else {
           throwExceptionAccordingToContext(contexto);
        }
        return documentTruncado;
    }

    private String throwExceptionAccordingToContext(String contexto) {
        switch (contexto) {
            case "cliente":
                throw new CpfIllegalArgException();
            case "empresa":
                throw new CnpjIllegalArgException();
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Trunca um documento removendo caracteres de formatação e retorna o documento truncado.
     *
     * <p>
     * Este método recebe um número de documento e verifica seu comprimento. Se o comprimento for igual a 14, assume-se que é um CNPJ
     * e remove os caracteres de formatação (pontos e traços) do CNPJ. Se o comprimento for igual a 18, assume-se que é um outro tipo de documento
     * e remove os caracteres de formatação (pontos, barras e traços). Se o comprimento não for 14 nem 18, o documento é retornado sem modificações.
     * </p>
     *
     * @param document O número de documento a ser truncado.
     * @return O documento truncado sem caracteres de formatação, se aplicável; caso contrário, retorna o documento original.
     */
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
