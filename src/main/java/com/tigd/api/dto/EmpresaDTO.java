package com.tigd.api.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

public record EmpresaDTO(
        String nome,
        @CNPJ(message = "CNPJ inválido")
        String cnpj,
        String email,
        BigDecimal saldo,
        BigDecimal taxaSistema
) {
}
