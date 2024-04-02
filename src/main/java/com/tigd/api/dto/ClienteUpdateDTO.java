package com.tigd.api.dto;

/**
 * @author gemeoslemes, vinicius lemes<br>
 *
 * <p>
 *  Um DTO (Data Transfer Object) que representa as informações que podem ser atualizar de um cliente.
 * </p>
 * @param nome O nome do Cliente.
 * @param email O email do Cliente.
 **/
public record ClienteUpdateDTO(
        String nome,
        String email
) {
}
