package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import com.tigd.api.domain.Transacao;
import com.tigd.api.exceptions.SaldoNegativoException;
import com.tigd.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TransacaoRepository repository;

    private Transacao verificarTipo(Transacao transacao) {
        char tipo = transacao.getTipo();
        if(tipo == 'D' || tipo == 'd') {
            return transacao;
        } else if (tipo == 'S' || tipo == 's') {
            return transacao;
        } else {
            throw new IllegalArgumentException("Tipo inválido");
        }
    }

    private void save(Transacao transacao) {
        repository.save(transacao);
    }


    public Transacao processarTransacao(Transacao transacao) {
        Optional<Cliente> cliente = clienteService.findById(transacao.getCliente().getId());
        Optional<Empresa> empresa = empresaService.findById(transacao.getEmpresa().getId());

        BigDecimal valorTransacao = transacao.getValor();
        BigDecimal taxaSistema = empresa.get().getTaxaSistema();
        Transacao tipoVerificado = verificarTipo(transacao);

        if (tipoVerificado.getTipo() == 'D') {
            processarDebito(cliente.get(), empresa.get(), valorTransacao, taxaSistema);
        } else if (tipoVerificado.getTipo() == 'S') {
            processarSaque(cliente.get(), empresa.get(), valorTransacao, taxaSistema);
        }

        save(transacao);
        return transacao;
    }

    private void processarDebito(Cliente cliente, Empresa empresa, BigDecimal valorTransacao, BigDecimal taxaSistema) {
        BigDecimal valorComTaxa = valorTransacao.subtract(valorTransacao.multiply(taxaSistema));
        BigDecimal novoSaldo = empresa.getSaldo().add(valorComTaxa);
        verificarSaldoSuficiente(cliente.getSaldo(), valorTransacao);

        cliente.setSaldo(cliente.getSaldo().subtract(valorTransacao));
        empresa.setSaldo(novoSaldo);

        clienteService.atualizarSaldo(cliente);
        empresaService.atualizarSaldo(empresa);
    }

    private void processarSaque(Cliente cliente, Empresa empresa, BigDecimal valorTransacao, BigDecimal taxaSistema) {
        BigDecimal valorComTaxa = valorTransacao.add(valorTransacao.multiply(taxaSistema));
        verificarSaldoSuficiente(empresa.getSaldo(), valorComTaxa);

        BigDecimal novoValorSaldoEmpresa = empresa.getSaldo().subtract(valorComTaxa);
        empresa.setSaldo(novoValorSaldoEmpresa);
        cliente.setSaldo(cliente.getSaldo().add(valorTransacao));

        empresaService.atualizarSaldo(empresa);
        clienteService.atualizarSaldo(cliente);
    }

    private void verificarSaldoSuficiente(BigDecimal saldo, BigDecimal valorTransacao) {
        if (saldo.compareTo(valorTransacao) < 0) {
            throw new SaldoNegativoException();
        }
    }



}
