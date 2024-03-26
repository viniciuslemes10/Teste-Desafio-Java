package com.tigd.api.dto;

import java.math.BigDecimal;

public record EmpresaUpdateDTO(
        String nome,
        String email,
        BigDecimal taxaSistema
) {
}
