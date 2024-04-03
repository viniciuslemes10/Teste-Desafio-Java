package com.tigd.api.dto;

import java.math.BigDecimal;

/**
 * @author viniciuslemes, gemeoslemes<br>
 * <p>
 * DTO (Data Transfer Object) que representa as informações que podem ser atualizada de uma Empresa.
 * </p>
 *
 * @param nome O nome da Empresa.
 * @param email O email da Empresa.
 * @param taxaSistema A taxa do sistema da Empresa.
 **/
public record EmpresaUpdateDTO(
        String nome,
        String email,
        BigDecimal taxaSistema
) {
}
