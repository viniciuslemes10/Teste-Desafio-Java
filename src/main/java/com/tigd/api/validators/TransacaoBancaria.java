package com.tigd.api.validators;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoBancaria {
    @Autowired
    private Empresa empresa;
    @Autowired
    private Cliente cliente;
    @Autowired
    private BigDecimal valorTransacao;
    @Autowired
    private BigDecimal valorComTaxa;
    @Autowired
    private boolean isDebito;


    public boolean getIsDebito() {
        return this.isDebito;
    }

    public void setValorComTaxa(BigDecimal valorComTaxa) {
        this.valorComTaxa = valorComTaxa;
    }

}
