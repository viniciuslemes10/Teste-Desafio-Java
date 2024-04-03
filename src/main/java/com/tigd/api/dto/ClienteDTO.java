package com.tigd.api.dto;


import java.math.BigDecimal;
/**
 * @author gemeoslemes, vinicius lemes<br>
 *
 * <p>
 *  Um DTO (Data Transfer Object) que representa as informações de um cliente.
 * </p>
 * @param nome O nome do Cliente.
 * @param email O email do Cliente.
 * @param saldo O saldo do Cliente.
 * @param cpf o CPF do Cliente.
 **/
public record ClienteDTO(
        String nome,
        String cpf,
        String email,
        BigDecimal saldo
) {
}
