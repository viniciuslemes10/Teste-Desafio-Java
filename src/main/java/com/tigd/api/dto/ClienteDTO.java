package com.tigd.api.dto;


import java.math.BigDecimal;

public record ClienteDTO(
        String nome,
        String cpf,
        String email,
        BigDecimal saldo
) {
}
