package com.tigd.api.validators;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import com.tigd.api.dto.ClienteUpdateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Classe responsável por validar e atualizar informações de Cliente e Empresa.
 *
 * Esta classe valida se os dados recebidos não são nulos ou vazios e, caso não sejam, atualiza as informações
 * das classes Cliente e Empresa.
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
 @Component
public class ValidadorAtualizadorEntidade {

    /**
     * Verifica se os campos nome, email e taxa do sistema da empresa não são nulos.
     * Se uma instância de empresa estiver presente no Optional, os campos serão atualizados
     * com os valores fornecidos, desde que não sejam nulos.
     *
     * @param empresa         a empresa contendo os valores a serem verificados e, possivelmente, atualizados.
     * @param empresaOptional um Optional que pode conter uma instância existente de empresa.
     * @return a instância de empresa presente no Optional, se existir.
     * @see #setValueIfNotNullOrEmpty(String, Consumer)
     * @see #setValueIfNotNull(BigDecimal, Consumer)
     */
    public Empresa verifyNameAndEmailAndRateSystemNotNull(Empresa empresa, Optional<Empresa> empresaOptional) {
        empresaOptional.ifPresent(empresaExistente -> {
            setValueIfNotNullOrEmpty(empresa.getNome(), empresaExistente::setNome);
            setValueIfNotNullOrEmpty(empresa.getEmail(), empresaExistente::setEmail);
            setValueIfNotNull(empresa.getTaxaSistema(), empresaExistente::setTaxaSistema);
        });
        return empresaOptional.get();
    }


    /**
     * Verifica se o valor não é nulo e nem vazio, caso ele não for o Consumer aceita o argumento,
     * se for vazio ou nulo ele não vai ser atualizado e nem aceito pelo Consumer.
     *
     * @param value  O valor a ser vericado.
     * @param setter caso não venha nulo ou vazio ele aceita o argumento.
     */
    private void setValueIfNotNullOrEmpty(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    /**
     * Verica se o valor é diferente de nulo, caso seja o Consumer aceita o argumento.
     *
     * @param value  O valor a ser vericado.
     * @param setter caso não venha nulo ele aceita o argumento.
     */
    private void setValueIfNotNull(BigDecimal value, Consumer<BigDecimal> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
