package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import com.tigd.api.domain.Transacao;
import com.tigd.api.exceptions.ElementNotFoundException;
import com.tigd.api.exceptions.SaldoNegativoException;
import com.tigd.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * @author gemeoslemes viniciuslemes10<br>
 * A classe TransacaoService fornece funcionalidades relacionada as transações entre empresas e clientes.
 * Ela permite registar novas transações, listar todas as transações já realizadas.
 * Está classe depende das classes {@link ClienteService}, {@link EmpresaService} e {@link TransacaoRepository}
 *para acessar e persistir informações de empresa e cliente.
 * **/
@Service
public class TransacaoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TransacaoRepository repository;

    /**
     * Verifica o tipo de transação que será realizada 'S' (saque) ou 'D' (depósito).
     * Caso contrário lança uma exceção IlegalArgumentExcetion.
     * @param transacao a transação a ser verificada.
     * @return a transação se for do tipo 'S'(saque) ou 'D' (depósito).
     **/
    private Transacao verificarTipo(Transacao transacao) {
        Character tipo = transacao.getTipo();
        Character.toUpperCase(tipo);
        return getTransacaoDepositoSaqueValidada(transacao, tipo);
    }

    /**
     * Caso o tipo seja 'D' (depositar) || 'S' (sacar) é válido.<br>
     * @param transacao A transação que será retornado se for válido.
     * @param tipo O tipo de transação que será verificado.
     * @return transacao se o tipo for válido, caso contrário ele lança exceção.<br>
     * @throws IllegalArgumentException se o tipo de transação não for 'S' ou 'D'.
     **/
    private Transacao getTransacaoDepositoSaqueValidada(Transacao transacao, Character tipo) {
        switch (tipo) {
            case 'D', 'S':
                return transacao;
            default:
                throw new IllegalArgumentException("Tipo de transação inválido: " + tipo);
        }
    }

    /**
     * Salva a nova transação realizada na base de dados.
     * @param transacao a transação que será salva.
     * @return A transação salvada.
     **/
    private void save(Transacao transacao) {
        repository.save(transacao);
    }

    /**
     * Processa uma Transação e atualiza os saldos do cliente e empresa.
     * @param transacao Transação que será processada. <br>
     * Os métodos {@link #findByCompany} e {@link #findbyClient} buscam o cliente e a empresa
     * que seram atualizado os saldos.
     * Outros métodos para realizar a transação são: ({@link #obterValorTransacao},
     * {@link #obterTaxaSistema}). {@link #verificarTipo} De acordo com o tipo se por passado como
     * 'S' (saque) será executado o método {@link #processarSaque}, caso for do tipo 'D' (depósito)
     * será executado o método {@link #processarDebito}).
     * @return Salva na base de dados, mostrando a transação processada.
     **/
    public Transacao processarTransacao(Transacao transacao) {
        Empresa empresa = findByCompany(transacao.getEmpresa().getId());
        Cliente cliente = findbyClient(transacao.getCliente().getId());

        BigDecimal valorTransacao = obterValorTransacao(transacao);
        BigDecimal taxaSistema = obterTaxaSistema(empresa);
        Transacao tipoVerificado = verificarTipo(transacao);

        switch (tipoVerificado.getTipo()){
            case 'D':
                processarDebito(cliente, empresa, valorTransacao, taxaSistema);
                break;
            case 'S':
                processarSaque(cliente, empresa, valorTransacao, taxaSistema);
                break;
        }

        save(transacao);
        return transacao;
    }

    /**
     * Obtém o valor da transação.
     *
     * @param transacao A transação da qual deseja-se obter o valor.
     * @return O valor da transação.
     */
    private BigDecimal obterValorTransacao(Transacao transacao) {
        return transacao.getValor();
    }

    /**
     * Obtém a taxa do sistema associada à empresa.
     *
     * @param empresa A empresa da qual deseja-se obter a taxa do sistema.
     * @return A taxa do sistema da empresa.
     */
    private BigDecimal obterTaxaSistema(Empresa empresa) {
        return empresa.getTaxaSistema();
    }

    /**
     * Localiza uma empresa com base no ID fornecido, verificando se é válida.
     *
     * @param empresaId O ID da empresa a ser localizada.
     * @return A empresa encontrada, ou null se não for encontrada ou não for válida.
     */
    private Empresa findByCompany(Long empresaId) {
        return findValidClientOrCompany(empresaService.findById(empresaId));
    }

    /**
     * Localiza um cliente com base no ID fornecido, verificando se é válido.
     *
     * @param clienteId O ID do cliente a ser localizado.
     * @return O cliente encontrado, ou null se não for encontrado ou não for válido.
     */
    private Cliente findbyClient(Long clienteId) {
        return findValidClientOrCompany(clienteService.findById(clienteId));
    }


    /**
     * Retorna o valor contido em um Optional, lançando uma exceção se o valor estiver ausente.
     *
     * @param optional O Optional do qual deseja-se obter o valor.
     * @param <T> O tipo do valor contido no Optional.
     * @return O valor contido no Optional.
     * @throws ElementNotFoundException Se o Optional estiver vazio.
     */
    private <T> T findValidClientOrCompany(Optional<T> optional) {
        return optional.orElseThrow(ElementNotFoundException::new);
    }

    /**
     * Processa uma transação de Saque, atualizando os saldos do cliente e da empresa.
     *
     * @param cliente O cliente associado à transação de débito.
     * @param empresa A empresa associada à transação de débito.
     * @param valorTransacao O valor da transação de débito.
     * @param taxaSistema A taxa do sistema aplicada à transação de débito.
     * @exception SaldoNegativoException Se o saldo do cliente for insuficiente para a transação.
     */
    private void processarSaque(Cliente cliente, Empresa empresa, BigDecimal valorTransacao, BigDecimal taxaSistema) {
        BigDecimal valorComTaxa = valorTransacao.add(valorTransacao.multiply(taxaSistema));
        verificarSaldoSuficiente(empresa.getSaldo(), valorComTaxa);

        BigDecimal novoValorSaldoEmpresa = empresa.getSaldo().subtract(valorComTaxa);
        empresa.setSaldo(novoValorSaldoEmpresa);
        cliente.setSaldo(cliente.getSaldo().add(valorTransacao));

        atualizaSaldoClienteEmpresa(empresa, cliente);
    }

    /**
     * Processa uma transação de débito, atualizando os saldos do cliente e da empresa.
     *
     * @param cliente O cliente associado à transação de débito.
     * @param empresa A empresa associada à transação de débito.
     * @param valorTransacao O valor da transação de débito.
     * @param taxaSistema A taxa do sistema aplicada à transação de débito.
     * @exception SaldoNegativoException Se o saldo do cliente for insuficiente para a transação.
     */
    private void processarDebito(Cliente cliente, Empresa empresa, BigDecimal valorTransacao, BigDecimal taxaSistema) {
        verificarSaldoSuficiente(cliente.getSaldo(), valorTransacao);
        BigDecimal valorComTaxa = valorTransacao.subtract(valorTransacao.multiply(taxaSistema));
        BigDecimal novoSaldo = empresa.getSaldo().add(valorTransacao);
        empresa.setSaldo(novoSaldo);
        cliente.setSaldo(cliente.getSaldo().subtract(valorComTaxa));
        atualizaSaldoClienteEmpresa(empresa, cliente);
    }

    /**
     * Atualiza o saldo do cliente e da empresa salvando na base de dados.
     * @param cliente O cliente associado a transação.
     * @param empresa A empresa associada a transação.
     **/
    private void atualizaSaldoClienteEmpresa(Empresa empresa, Cliente cliente) {
        clienteService.atualizarSaldo(cliente);
        empresaService.atualizarSaldo(empresa);
    }

    /**
     * Verifica se o saldo é suficiente para realizar a transação com o valor passado.
     * @param valorTransacao valor que é passado na transação.
     * @param saldo O saldo que o cliente ou a empresa tem na base de dados.
     * @exception SaldoNegativoException caso saldo seja menor que o valor passado, a exception é passada
     * com a mensagem e seu status.
     **/
    private void verificarSaldoSuficiente(BigDecimal saldo, BigDecimal valorTransacao) {
        if (saldo.compareTo(valorTransacao) < 0) {
            throw new SaldoNegativoException();
        }
    }

    /**
     * Lista todas as transações já cadastradas na base de dados.
     **/
    public List<Transacao> findAllTransacoes() {
        return repository.findAll();
    }
}
