package com.tigd.api.dto;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author gemeoslemes, vinicius lemes<br>
 *
 * <p>
 *  Um DTO (Data Transfer Object) que representa as informações de uma transação.
 * </p>
 * @param valor O valor da Transação.
 * @param tipo O tipo de transação 'S' (Saque) ou 'D' (depósito).
 * @param cliente O Cliente referente a Transação.
 * @param empresa A Empresa referente a Transação.
 **/
public record TransacaoDTO(

        BigDecimal valor,
        char tipo,
        Long cliente,
        Long empresa
) {}
