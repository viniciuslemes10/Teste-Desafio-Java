package com.tigd.api.dto;

import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record ClienteDTO(
        String nome,
        @CPF(message = "CPF inválido")
        String cpf,
        String email,
        BigDecimal saldo
) {
}
