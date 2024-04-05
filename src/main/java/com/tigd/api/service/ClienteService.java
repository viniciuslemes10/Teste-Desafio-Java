package com.tigd.api.service;

import com.tigd.api.dto.ClienteUpdateDTO;
import com.tigd.api.exceptions.*;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.domain.Cliente;
import com.tigd.api.validators.ValidadorAtualizadorEntidade;
import com.tigd.api.validators.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 @author gemeoslemes viniciuslemes10 <br>
 Classe de <b>ClienteService</b>
 impleta os métodos para manipulação e inserção na base de dados.
 **/
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DocumentValidator documentValidator;

    @Autowired
    private ValidadorAtualizadorEntidade atualizadorEntidade;

    /**
     * Retorna uma lista de todos os clientes
     * @return Uma lista contendo todos os clientes.
     **/
    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }

    /**
     * Salva um cliente na base de dados.
     *
     * Este método recebe um cliente como parâmetro, valida o CPF ou CNPJ do cliente,
     * e então salva o cliente na base de dados.
     *
     * @param cliente o cliente a ser salvo na base de dados.
     * @return o cliente salvo na base de dados na entidade 'clientes'.
     * @see #isCpfPresent(Cliente)
     * @see #isEmailPresent(Cliente)
     */
    public Cliente save(Cliente cliente) {
        String documentCpf = documentValidator.isValid(cliente.getCpf(), "cliente");
        cliente.setCpf(documentCpf);
        isCpfPresent(cliente);
        isEmailPresent(cliente);
        return clienteRepository.save(cliente);
    }

    /**
     * @param cliente O cliente é passado como parâmetro.
     * Este método verifica se o cpf já está cadastrado em
     * nossa base de dados.
     **/
    protected void isCpfPresent(Cliente cliente) {
        boolean clienteCpf = findByCpf(cliente);
        if(clienteCpf) {
            throw new CpfUniqueException();
        }
    }

    /**
     * @param cliente O Cliente é passado como parâmetro.<br>
     * Método <b>findByCpf()</b> verifica se o cliente é diferente de null,
     *  se o resultado esperado for true.
     * @return cliente.
     * **/
    protected boolean findByCpf(Cliente cliente) {
        Cliente clientes = clienteRepository.findByCpf(cliente.getCpf());
        return clientes != null;
    }

    /**
     @param cliente cliente.
     <p>Buscamos por email e verificamos se este email
     já está cadastrado, caso for true, lança uma Exception.</p>
     @exception EmailUniqueException Quando chamada lança uma mensagem de erro
     e seu status.
     **/
    protected void isEmailPresent(Cliente cliente) {
        boolean clienteEmail = findByEmail(cliente);
        if(clienteEmail) {
            throw new EmailUniqueException();
        }
    }

    /**
     * @param cliente cliente.
     * @return <p>Um cliente se for diferente de null.</p>
     **/
    protected boolean findByEmail(Cliente cliente) {
       Cliente clientes = clienteRepository.findByEmail(cliente.getEmail());
       return clientes != null;
    }

    /**
     @param id id.
     @return Retorna um cliente buscado pelo id.
     **/
    public Optional<Cliente> findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            return cliente;
        } else {
            throw new ClienteNotFoundException();
        }
    }


    /**
     @param cliente cliente.
     @return Salvando cliente com saldo atualizado na base de dados.
     **/
    public Cliente atualizarSaldo(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * @param dto cliente
     * @param id id
     * @return <p>Retorna um cliente atualizado.</p>
     **/
    public Cliente updateClient(ClienteUpdateDTO dto, Long id) {
        Optional<Cliente> clientById = findById(id);
        Cliente clienteAtualizado = atualizaDados(dto, clientById);
        Cliente clienteAtivo = byAtivo(clienteAtualizado.getId(), clienteAtualizado.isAtivo());
        validarContaAtiva(clienteAtivo);
        isEmailPresent(clienteAtivo);
        return clienteRepository.save(clienteAtualizado);
    }

    protected Cliente atualizaDados(ClienteUpdateDTO dto, Optional<Cliente> cliente) {
        Cliente clienteExistente = cliente.get();
        if (!dto.nome().trim().equals(clienteExistente.getNome())) {
            clienteExistente.setNome(dto.nome().trim());
        }
        if (!dto.email().trim().equals(clienteExistente.getEmail())) {
            clienteExistente.setEmail(dto.email().trim());
        }
        return clienteExistente;
    }

    protected Cliente byAtivo(Long id, boolean ativo) {
        return clienteRepository.findByAtivo(id, ativo);
    }

    /**
     * Valida se a conta do cliente está ativa.
     *
     * @param cliente o cliente cuja conta será validada.
     * @throws ContaInativaException se a conta do cliente estiver inativa.
     */
    protected void validarContaAtiva(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new ContaInativaException();
        }
    }


    /**
     * @param cliente cliente
     * <p>Recebe como parâmetro um cliente Optional onde pode ou não vir.
     * Transformando em um cliente da Classe Cliente, mundando o estado de ativo para false,
     * salvando na base de dados.</p>
     * @return cliente.
     ***/
    public Cliente deleteClientById(Optional<Cliente> cliente) {
        Cliente clienteInativado = cliente.get();
        clienteInativado.setAtivo(false);
        clienteRepository.save(clienteInativado);
        return clienteInativado;
    }
}
