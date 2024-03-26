package com.tigd.api.infra;

import com.tigd.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CnpjIllegalArgException.class)
    private ResponseEntity<RestErrorMenssage> empresaIllegalArgument(CnpjIllegalArgException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

    @ExceptionHandler(CpfIllegalArgException.class)
    private ResponseEntity<RestErrorMenssage> clienteIllegalArgument(CpfIllegalArgException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

    @ExceptionHandler(EmailUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeEmailUnique(EmailUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }
    @ExceptionHandler(CpfUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeCpfUnique(CpfUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    @ExceptionHandler(CnpjUniqueException.class)
    private ResponseEntity<RestErrorMenssage> atributeCnpjUnique(CnpjUniqueException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    @ExceptionHandler(SaldoNegativoException.class)
    private ResponseEntity<RestErrorMenssage> saldoInsuficiente(SaldoNegativoException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    private ResponseEntity<RestErrorMenssage> clienteOrEmpresaNotFound(ElementNotFoundException exception) {
        RestErrorMenssage restError = new RestErrorMenssage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restError);
    }
}
