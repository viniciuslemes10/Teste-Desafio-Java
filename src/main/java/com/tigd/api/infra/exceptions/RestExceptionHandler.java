package com.tigd.api.infra.exceptions;

import com.tigd.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * @author gemeoslemes, viniciuslemes<br>
 * Classe responsável por lidar com exceções e gerar respostas adequadas para solicitações REST.
 *
 * <p>
 * Está classe é anotada com {@code @RestControllerAdvice}, o que torna capaz de interceptar exceções
 * lançadas nos métodos anotados {@code @RequestMapping} ou outras anotações de mapeamento de controlador.
 * </p>
 *
 * <p>
 * Quando uma exceção específica é lançada durante o processamento de uma solicitação, os métodos nesta
 * classe podem capturá-la. E gera uma resposta HTTP adequada com base na exceção capturada.
 *
 * </p>
 **/
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Método por lidar com a exceção {@link CnpjIllegalArgException}.
     *
     * <p>
     * Este método é acionado quando uma {@code CnpjIllegalArgException} é lançada durante o processameto
     * de uma solicitação REST.
     * Ela cria uma instância de {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#BAD_REQUEST} e a
     * mensagem de erro fornecida pela exceção, e a retorna em uma resposta HTTP com status {@link HttpStatus#BAD_REQUEST}.
     *
     * </p>
     * @param exception A exceção {@link CnpjIllegalArgException} capturada.
     * @return Uma resposta HTTP com o status {@link HttpStatus#BAD_REQUEST} contendo a mensagem de erro capturada.
     * @see CnpjIllegalArgException
     * @see RestErrorMenssage
     **/
    @ExceptionHandler(CnpjIllegalArgException.class)
    private ResponseEntity<RestErrorMenssage> empresaIllegalArgument(CnpjIllegalArgException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

    /**
     * Método para lidar com a exceção {@link CpfIllegalArgException}.
     * <p>
     * Este método é acionado quando uma {@code CpfIlegalArgException} é lançada durante o processamento
     * de uma solicitação REST.
     * Ela cria uma instância {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#BAD_REQUEST}
     * e a mensagem de erro fornecida pela exceção, e retorna uma resposta HTTP com o status {@link HttpStatus#BAD_REQUEST}.
     *
     * </p>
     * @param exception A exceção {@link CpfIllegalArgException} capturada.
     * @return Uma resposta com o status HTTP {@link HttpStatus#BAD_REQUEST} contendo a mensagem de erro capturada.
     * @see CpfIllegalArgException
     * @see ResponseEntity
     **/
    @ExceptionHandler(CpfIllegalArgException.class)
    private ResponseEntity<RestErrorMenssage> clienteIllegalArgument(CpfIllegalArgException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

   /**
    * Método para lidar com a exceção {@link EmailUniqueException}.
    *
    * <p>
    * Este método é acionado quando uma {@link EmailUniqueException} é lançada durante o processamento
    * de uma solicitação REST.
    * Ela cria uma instância {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#CONFLICT} e a mensagem
    * de erro fornecida pela exceção, e retorna uma resposta com o status HTTP {@link HttpStatus#CONFLICT}.
    *
    * </p>
    *
    * @param exception A exceção {@link EmailUniqueException} capturada.
    * @return Uma resposta com o status HTTP {@link HttpStatus#CONFLICT} com a mensagem de erro capturada.
    * @see RestErrorMenssage
    * @see EmailUniqueException
    **/
    @ExceptionHandler(EmailUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeEmailUnique(EmailUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }


    /**
     * Método para lidar com a exceção {@link CpfUniqueException}.
     *
     * <p>
     * Este método é acionado quando uma {@link CpfUniqueException} é lançada durante o processamento
     * de uma solicitação REST.
     * Ela cria uma instância {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#CONFLICT} e a mensagem
     * de erro fornecida pela exceção, e retorna uma resposta com o status HTTP {@link HttpStatus#CONFLICT}.
     *
     * </p>
     *
     * @param exception A exceção {@link CpfUniqueException} capturada.
     * @return Uma resposta com o status HTTP {@link HttpStatus#CONFLICT} com a mensagem de erro capturada.
     * @see RestErrorMenssage
     * @see CpfUniqueException
     **/
    @ExceptionHandler(CpfUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeCpfUnique(CpfUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    /**
     * Método para lidar com a exceção {@link CnpjUniqueException}.
     *
     * <p>
     * Este método é acionado quando uma {@link CnpjUniqueException} é lançada durante o processamento
     * de uma solicitação REST.
     * Ela cria uma instância {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#CONFLICT} e a mensagem
     * de erro fornecida pela exceção, e retorna uma resposta com o status HTTP {@link HttpStatus#CONFLICT}.
     *
     * </p>
     *
     * @param exception A exceção {@link CnpjUniqueException} capturada.
     * @return Uma resposta com o status HTTP {@link HttpStatus#CONFLICT} com a mensagem de erro capturada.
     * @see RestErrorMenssage
     * @see CnpjUniqueException
     **/
    @ExceptionHandler(CnpjUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeCnpjUnique(CnpjUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    /**
     * Método responsável por lidar com a exceção {@link SaldoNegativoException}.
     *
     * <p>
     * Este método é acionado quando uma {@code SaldoNegativoException} é lançada durante o processamento de uma solicitação REST.
     * Ele cria uma instância de {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#BAD_REQUEST} e a mensagem de erro
     * fornecida pela exceção, e a retorna em uma resposta HTTP com status {@link HttpStatus#BAD_REQUEST}.
     * </p>
     *
     * @param exception A exceção {@code SaldoNegativoException} capturada.
     * @return Uma resposta HTTP com status {@code BAD_REQUEST} contendo a mensagem de erro adequada.
     * @see SaldoNegativoException
     * @see RestErrorMenssage
     */
    @ExceptionHandler(SaldoNegativoException.class)
    private ResponseEntity<RestErrorMenssage> saldoInsuficiente(SaldoNegativoException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

    /**
     * Método responsável por lidar com a exceção {@link ElementNotFoundException}.
     *
     * <p>
     * Este método é acionado quando uma {@code ElementNotFoundException} é lançada durante o processamento de uma solicitação REST.
     * Ele cria uma instância de {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#NOT_FOUND} e a mensagem de erro
     * fornecida pela exceção, e a retorna em uma resposta HTTP com status {@link HttpStatus#NOT_FOUND}.
     * </p>
     *
     * @param exception A exceção {@code ElementNotFoundException} capturada.
     * @return Uma resposta HTTP com status {@code NOT_FOUND} contendo a mensagem de erro adequada.
     * @see ElementNotFoundException
     * @see RestErrorMenssage
     */
    @ExceptionHandler(ElementNotFoundException.class)
    private ResponseEntity<RestErrorMenssage> clienteOrEmpresaNotFound(ElementNotFoundException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restError);
    }

    /**
     * Método responsável por lidar com a exceção {@link ClienteNotFoundException}.
     *
     * <p>
     * Este método é acionado quando uma {@code ClienteNotFoundException} é lançada durante o processamento de uma solicitação REST.
     * Ele cria uma instância de {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#NOT_FOUND} e a mensagem de erro
     * fornecida pela exceção, e a retorna em uma resposta HTTP com status {@link HttpStatus#NOT_FOUND}.
     * </p>
     *
     * @param exception A exceção {@code ClienteNotFoundException} capturada.
     * @return Uma resposta HTTP com status {@code NOT_FOUND} contendo a mensagem de erro adequada.
     * @see ClienteNotFoundException
     * @see RestErrorMenssage
     */
    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<RestErrorMenssage> clienteNotFoundException(ClienteNotFoundException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restError);
    }


    /**
     * Método responsável por lidar com a exceção {@link EmpresaNotFoundException}.
     *
     * <p>
     * Este método é acionado quando uma {@code EmpresaNotFoundException} é lançada durante o processamento de uma solicitação REST.
     * Ele cria uma instância de {@link RestErrorMenssage} com o status HTTP {@link HttpStatus#NOT_FOUND} e a mensagem de erro
     * fornecida pela exceção, e a retorna em uma resposta HTTP com status {@link HttpStatus#NOT_FOUND}.
     * </p>
     *
     * @param exception A exceção {@code EmpresaNotFoundException} capturada.
     * @return Uma resposta HTTP com status {@code NOT_FOUND} contendo a mensagem de erro adequada.
     * @see EmpresaNotFoundException
     * @see RestErrorMenssage
     */
    @ExceptionHandler(EmpresaNotFoundException.class)
    private ResponseEntity<RestErrorMenssage> empresaNotFoundException(EmpresaNotFoundException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restError);
    }

    @ExceptionHandler(ContaInativaException.class)
    private ResponseEntity<RestErrorMenssage> contaInativaException(ContaInativaException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }
}
