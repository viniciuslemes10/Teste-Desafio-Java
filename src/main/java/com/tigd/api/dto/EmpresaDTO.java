package com.tigd.api.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
/**
 * @author gemeoslemes, vinicius lemes<br>
 *
 * <p>
 *  Um DTO (Data Transfer Object) que representa as informações de uma empresa.
 * @param taxaSistema A taxa do sistema da Empresa.
 * @param saldo O saldo da Empresa.
 * @param cnpj O CNPJ da Empresa.
 * @param email O Email da Empresa.
 * @param nome O nome da Empresa.
 * </p>
 **/
public record EmpresaDTO(
        String nome,
        String cnpj,
        String email,
        BigDecimal saldo,
        BigDecimal taxaSistema
) {
}
