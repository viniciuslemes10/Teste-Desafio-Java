package com.tigd.api.dto;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransacaoDTO(

        BigDecimal valor,
        char tipo,
        Long cliente,
        Long empresa
) {}
