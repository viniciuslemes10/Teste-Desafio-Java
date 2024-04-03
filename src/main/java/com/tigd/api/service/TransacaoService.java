package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import com.tigd.api.domain.Transacao;
import com.tigd.api.exceptions.ElementNotFoundException;
import com.tigd.api.exceptions.SaldoNegativoException;
import com.tigd.api.repository.TransacaoRepository;
import com.tigd.api.validators.TransacaoBancaria;
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
 * para acessar e persistir informações de empresa e cliente.
 **/
@Service
public class TransacaoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TransacaoRepository repository;

    /**
     * Verifica o tipo de transação que será realizada 'S' (saque) ou 'D' (depósito).<br>
     * Caso contrário, lança uma exceção IllegalArgumentException.
     * Ele chama o método {@link #obterTransacaoDepositoOuSaqueValidada obterTransacaoDepositoOuSaqueValidada(Transacao, char)}.
     *
     * @param transacao a transação a ser verificada.
     * @return a transação se for do tipo 'S' (saque) ou 'D' (depósito).
     * @throws IllegalArgumentException se o tipo da transação não for 'S' ou 'D'.
     */
    private Transacao validarTipoTransacao(Transacao transacao) {
        Character tipo = Character.toUpperCase(transacao.getTipo());
        return obterTransacaoDepositoOuSaqueValidada(transacao, tipo);
    }

    /**
     * Caso o tipo seja 'D' (depositar) || 'S' (sacar) é válido.<br>
     *
     * @param transacao A transação que será retornado se for válido.
     * @param tipo      O tipo de transação que será verificado.
     * @return transacao se o tipo for válido, caso contrário ele lança exceção.<br>
     * @throws IllegalArgumentException se o tipo de transação não for 'S' ou 'D'.
     **/
    private Transacao obterTransacaoDepositoOuSaqueValidada(Transacao transacao, Character tipo) {
        switch (tipo) {
            case 'D', 'S':
                return transacao;
            default:
                throw new IllegalArgumentException("Tipo de transação inválido: " + tipo);
        }
    }

    /**
     * Salva a nova transação realizada na base de dados.
     *
     * @param transacao a transação que será salva.
     **/
    private void save(Transacao transacao) {
        repository.save(transacao);
    }

    /**
     * Processa uma transação e atualiza os saldos do cliente e da empresa.
     *
     * @param transacao a transação que será processada.
     * @return a transação processada que foi salva na base de dados.
     * @see #validarTipoTransacao(Transacao)
     * @see #processarTipoEspecifico(Transacao)
     * @see #save(Transacao)
     */
    public Transacao processarTransacao(Transacao transacao) {
        Transacao tipoVerificado = validarTipoTransacao(transacao);
        processarTipoEspecifico(tipoVerificado);
        save(transacao);
        return transacao;
    }

    /**
     * Processa o tipo específico de transação com base no tipo especificado na transação.
     *
     * @param transacao a transação que será processada.
     * @see #processarTransacao(Transacao, boolean)
     */
    private void processarTipoEspecifico(Transacao transacao) {
        switch (transacao.getTipo()) {
            case 'D':
                processarTransacao(transacao, true);
                break;
            case 'S':
                processarTransacao(transacao, false);
                break;
        }
    }

    /**
     * Processa uma transação bancária com base nas informações fornecidas. Isso inclui encontrar
     * a empresa e o cliente associados à transação, obter o valor da transação e a taxa do sistema,
     * criar uma instância de TransacaoBancaria com essas informações, processar o movimento financeiro
     * com validação de saldo, executar a transação com base no tipo e atualizar os saldos do cliente e da empresa.
     *
     * @param transacao a transação bancária a ser processada.
     * @param isDebito indica se a transação é um débito ou não.
     * @see #encontrarEmpresa(Transacao)
     * @see #encontrarCliente(Transacao)
     * @see #obterValorTransacao(Transacao)
     * @see #obterTaxaSistema(Empresa)
     * @see #processarMovimentoFinanceiroComValidacaoDeSaldo(TransacaoBancaria)
     * @see #executarTransacaoComBaseNoTipo(TransacaoBancaria)
     * @see #atualizaSaldoClienteEmpresa(Empresa, Cliente)
     */
    private void processarTransacao(Transacao transacao, boolean isDebito) {
        Empresa empresa = encontrarEmpresa(transacao);
        Cliente cliente = encontrarCliente(transacao);

        BigDecimal valorTransacao = obterValorTransacao(transacao);
        BigDecimal taxaSistema = obterTaxaSistema(empresa);

        TransacaoBancaria operacaoBancaria = new TransacaoBancaria(empresa, cliente, valorTransacao, taxaSistema, isDebito);

        processarMovimentoFinanceiroComValidacaoDeSaldo(operacaoBancaria);

        executarTransacaoComBaseNoTipo(operacaoBancaria);

        atualizaSaldoClienteEmpresa(operacaoBancaria.getEmpresa(), operacaoBancaria.getCliente());
    }

    /**
     * Processa o movimento financeiro de uma transação bancária, incluindo a validação de saldo.
     * Este método verifica se a transação é um débito ou não e realiza as seguintes etapas:<br>
     * - Se for um débito, verifica se o saldo do cliente é suficiente para a transação e,
     *   em seguida, calcula o valor da transação com a taxa aplicada.
     * - Se não for um débito, calcula o valor da transação com a taxa aplicada e,
     *   em seguida, verifica se o saldo da empresa é suficiente para a transação.
     *
     * @param transacaoBancaria a transação bancária a ser processada.
     * @see #verificarSaldoSuficiente(BigDecimal, BigDecimal) método usado para verificar se o saldo é suficiente.
     * @see #calcularValorComTaxa(BigDecimal, BigDecimal) método usado para calcular o valor da transação com a taxa aplicada.
     */
    private void processarMovimentoFinanceiroComValidacaoDeSaldo(TransacaoBancaria transacaoBancaria) {
        if (transacaoBancaria.getIsDebito()) {
            verificarSaldoSuficiente(transacaoBancaria.getCliente().getSaldo(), transacaoBancaria.getValorTransacao());
            transacaoBancaria.setValorComTaxa(calcularValorComTaxa(transacaoBancaria.getValorTransacao(), transacaoBancaria.getValorComTaxa()));
        } else {
            transacaoBancaria.setValorComTaxa(calcularValorComTaxa(transacaoBancaria.getValorTransacao(), transacaoBancaria.getValorComTaxa()));
            verificarSaldoSuficiente(transacaoBancaria.getEmpresa().getSaldo(), transacaoBancaria.getValorComTaxa());
        }
    }

    /**
     * Localiza a empresa associada à transação bancária fornecida.
     *
     * @param transacao a transação bancária para a qual encontrar a empresa.
     * @return a empresa associada à transação bancária.
     */
    private Empresa encontrarEmpresa(Transacao transacao) {
        return findByCompany(transacao.getEmpresa().getId());
    }

    /**
     * Localiza o cliente associado à transação bancária fornecida.
     *
     * @param transacao a transação bancária para a qual encontrar o cliente.
     * @return o cliente associado à transação bancária.
     */
    private Cliente encontrarCliente(Transacao transacao) {
        return findbyClient(transacao.getCliente().getId());
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
     * Verifica se o saldo é suficiente para realizar a transação com o valor passado.
     *
     * @param valorTransacao valor que é passado na transação.
     * @param saldo          O saldo que o cliente ou a empresa tem na base de dados.
     * @throws SaldoNegativoException caso saldo seja menor que o valor passado, a exception é passada
     *                                com a mensagem e seu status.
     **/
    private void verificarSaldoSuficiente(BigDecimal saldo, BigDecimal valorTransacao) {
        if (saldo.compareTo(valorTransacao) < 0) {
            throw new SaldoNegativoException();
        }
    }

    /**
     * Calcula o valor da transação com a taxa do sistema aplicada.
     *
     * @param valorTransacao o valor da transação antes da aplicação da taxa.
     * @param taxaSistema a taxa do sistema a ser aplicada à transação.
     * @return o valor da transação após a aplicação da taxa do sistema.
     */
    private BigDecimal calcularValorComTaxa(BigDecimal valorTransacao, BigDecimal taxaSistema) {
        return valorTransacao.add(valorTransacao.multiply(taxaSistema));
    }

    /**
     * Executa a transação bancária com base no tipo especificado na transação.
     * <br>
     * Este método verifica se a transação é um débito ou não e, em seguida, executa a transação
     * correspondente com base no tipo. Se a transação for um débito, chama o método para realizar
     * a transação de depósito. Caso contrário, chama o método para realizar a transação de saque.
     * <br>
     * @param transacaoBancaria a transação bancária a ser executada.
     * @see #realizarTransacaoDeDepositar(TransacaoBancaria) método para realizar uma transação de depósito.
     * @see #realizarTransacaoDeSaque(TransacaoBancaria) método para realizar uma transação de saque.
     */
    private void executarTransacaoComBaseNoTipo(TransacaoBancaria transacaoBancaria) {
        if (transacaoBancaria.getIsDebito()) {
            realizarTransacaoDeDepositar(transacaoBancaria);
        } else {
            realizarTransacaoDeSaque(transacaoBancaria);
        }
    }

    /**
     * Realiza uma transação de saque bancário.
     *
     * Este método calcula o valor total a ser retirado do saldo da empresa e adicionado ao saldo do cliente
     * com base no valor da transação e na taxa aplicada. Em seguida, atualiza os saldos da empresa e do cliente
     * de acordo com o resultado do saque.
     *
     * @param operacaoDeSaque a transação bancária de saque a ser processada.
     * @see #calculandoValorTotalSacado(Empresa, BigDecimal) método para calcular o valor total sacado.
     */
    private void realizarTransacaoDeSaque(TransacaoBancaria operacaoDeSaque) {
        BigDecimal valorASePagar = calculandoValorTotalSacado(operacaoDeSaque.getEmpresa(), operacaoDeSaque.getValorComTaxa());
        operacaoDeSaque.getEmpresa().setSaldo(valorASePagar);
        operacaoDeSaque.getCliente().setSaldo(operacaoDeSaque.getCliente().getSaldo().add(operacaoDeSaque.getValorTransacao()));
    }

    /**
     * Calcula o valor total a ser deduzido do saldo da empresa pagadora após uma transação de saque.
     *
     * Este método subtrai o valor da transação, incluindo a taxa aplicada, do saldo da empresa pagadora
     * para determinar o valor total a ser retirado do saldo da empresa após a realização de um saque.
     *
     * @param empresaPagadora a empresa da qual será deduzido o valor da transação.
     * @param valorComTaxa o valor da transação, incluindo a taxa aplicada.
     * @return o valor total a ser deduzido do saldo da empresa pagadora.
     */
    private BigDecimal calculandoValorTotalSacado(Empresa empresaPagadora, BigDecimal valorComTaxa) {
        return empresaPagadora.getSaldo().subtract(valorComTaxa);
    }

    /**
     * Realiza uma transação de depósito bancário.
     *
     * Este método adiciona o valor da transação ao saldo da empresa depositante e
     * subtrai o valor total da transação (incluindo a taxa aplicada) do saldo do cliente.
     *
     * @param operacaoDeDebito a transação bancária de depósito a ser processada.
     */
    private void realizarTransacaoDeDepositar(TransacaoBancaria operacaoDeDebito) {
        operacaoDeDebito.getEmpresa().setSaldo(operacaoDeDebito.getEmpresa().getSaldo().add(operacaoDeDebito.getValorTransacao()));
        operacaoDeDebito.getCliente().setSaldo(operacaoDeDebito.getCliente().getSaldo().subtract(operacaoDeDebito.getValorComTaxa()));
    }

    /**
     * Localiza uma empresa com base no ID fornecido, verificando se é válida.
     *
     * @param empresaId O ID da empresa a ser localizada.
     * @return A empresa encontrada, ou null se não for encontrada ou não for válida.
     */
    private Empresa findByCompany(Long empresaId) {
        return encontrarClienteOuEmpresaValido(empresaService.buscarEmpresaPorId(empresaId));
    }

    /**
     * Localiza um cliente com base no ID fornecido, verificando se é válido.
     *
     * @param clienteId O ID do cliente a ser localizado.
     * @return O cliente encontrado, ou null se não for encontrado ou não for válido.
     */
    private Cliente findbyClient(Long clienteId) {
        return encontrarClienteOuEmpresaValido(clienteService.findById(clienteId));
    }

    /**
     * Retorna o valor contido em um Optional, lançando uma exceção se o valor estiver ausente.
     *
     * @param optional O Optional do qual deseja-se obter o valor.
     * @param <T>      O tipo do valor contido no Optional.
     * @return O valor contido no Optional.
     * @throws ElementNotFoundException Se o Optional estiver vazio.
     */
    private <T> T encontrarClienteOuEmpresaValido(Optional<T> optional) {
        return optional.orElseThrow(ElementNotFoundException::new);
    }

    /**
     * Atualiza o saldo do cliente e da empresa salvando na base de dados.
     *
     * @param cliente O cliente associado a transação.
     * @param empresa A empresa associada a transação.
     **/
    private void atualizaSaldoClienteEmpresa(Empresa empresa, Cliente cliente) {
        clienteService.atualizarSaldo(cliente);
        empresaService.salvarEmpresa(empresa);
    }

    /**
     * Lista todas as transações já cadastradas na base de dados.
     **/
    public List<Transacao> findAllTransacoes() {
        return repository.findAll();
    }
}