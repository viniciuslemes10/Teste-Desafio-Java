package com.tigd.api.infra.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author viniciuslemes10
 * @author gemeoslemes<br>
 * Representa uma mensagem de erro que utiliza em respostas de serviços REST.
 *
 * <p>
 *     Está classe contém informações sobre o status HTTP e a mensagem de erro associada,
 *     que podem ser enviadas como resposta a uma requisição HTTP em um serviço REST.
 * </p>
 *
 * <p>
 *  Os objetos desta classe são utilizados para encapsular informações sobre erros ocorridos durante
 *  o processamento de requesições REST, fornecendo detalhes úteis sobre o erro ocorrido.
 *   <p>
 *   Exemplo de uso:
 *   <pre>{@code
 *   RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, "Erro ao processar a requisição");
 *   }</pre>
 *   </p>
 * </p>
 **/
@AllArgsConstructor
@Getter
@Setter
public class RestErrorMenssage {
    private HttpStatus status;
    private String message;
}
