package com.tigd.api.service;

import com.tigd.api.exceptions.ClienteNotFoundException;
import com.tigd.api.exceptions.CpfUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.exceptions.CpfIllegalArgException;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.domain.Cliente;
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

    /**
     * Retorna uma lista de todos os clientes
     * @return Uma lista contendo todos os clientes.
     **/
    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }
    /**
     * Verifica se o
     * @param cliente O cliente é passado como parâmetro
     * @return Salva um cliente na base de dados na Entity 'clientes'
     **/
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
    private void isCpfPresent(Cliente cliente) {
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
    private boolean findByCpf(Cliente cliente) {
        Cliente clientes = clienteRepository.findByCpf(cliente.getCpf());
        System.out.println(clientes);
        return clientes != null;
    }
    /**
     @param cliente cliente.
     <p>Buscamos por email e verificamos se este email
     já está cadastrado, caso for true, lança uma Exception.</p>
     @exception EmailUniqueException Quando chamada lança uma mensagem de erro
     e seu status.
     **/
    private void isEmailPresent(Cliente cliente) {
        boolean clienteEmail = findByEmail(cliente);
        if(clienteEmail) {
            throw new EmailUniqueException();
        }
    }
    /**
     * @param cliente cliente.
     * @return <p>Um cliente se for diferente de null.</p>
     **/
    private boolean findByEmail(Cliente cliente) {
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
     * @param cliente cliente
     * @param id id
     * @return <p>Retorna um cliente atualizado.</p>
     **/
    public Cliente updateClient(Cliente cliente, Long id) {
        Optional<Cliente> clientById = findById(id);
        Cliente clienteAtualizado = verifyNameAndEmailNotNull(cliente, clientById);
        return update(clienteAtualizado);
    }
    /**
     * @param cliente cliente
     * @param clienteOptional clienteOptional
     * <p>Verifica se o <b>cliente(nome, email)</b> são diferetes de null e
     *    vazios.</p>
     * @return clienteOptional
     **/
    private Cliente verifyNameAndEmailNotNull(Cliente cliente, Optional<Cliente> clienteOptional) {
        if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            clienteOptional.get().setNome(cliente.getNome());
        }
        if ( cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
            clienteOptional.get().setEmail(cliente.getEmail());
        }
        return clienteOptional.get();
    }
    /**
     * @param cliente cliente
     * @return <p>return um cliente salvando na base de dados.</p>
     **/
    private Cliente update(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente deleteClientById(Optional<Cliente> cliente) {
        Cliente client = cliente.get();
        client.setAtivo(false);
        clienteRepository.save(client);
        return client;
    }
}
