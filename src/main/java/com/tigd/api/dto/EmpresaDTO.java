package com.tigd.api.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

public record EmpresaDTO(
        String nome,
        String cnpj,
        String email,
        BigDecimal saldo,
        BigDecimal taxaSistema
) {
}
