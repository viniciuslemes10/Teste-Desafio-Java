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

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DocumentValidator documentValidator;

    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        String documentCpf = documentValidator.isValid(cliente.getCpf(), "cliente");
        cliente.setCpf(documentCpf);
        isCpfPresent(cliente);
        isEmailPresent(cliente);
        return clienteRepository.save(cliente);
    }

    private void isCpfPresent(Cliente cliente) {
        boolean clienteCpf = findByCpf(cliente);
        if(clienteCpf) {
            throw new CpfUniqueException();
        }
    }

    private boolean findByCpf(Cliente cliente) {
        Cliente clientes = clienteRepository.findByCpf(cliente.getCpf());
        System.out.println(clientes);
        return clientes != null;
    }

    private void isEmailPresent(Cliente cliente) {
        boolean clienteEmail = findByEmail(cliente);
        if(clienteEmail) {
            throw new EmailUniqueException();
        }
    }

    private boolean findByEmail(Cliente cliente) {
       Cliente clientes = clienteRepository.findByEmail(cliente.getEmail());
       return clientes != null;
    }

    public Optional<Cliente> findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            return cliente;
        } else {
            throw new ClienteNotFoundException();
        }
    }

    public Cliente atualizarSaldo(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateClient(Cliente cliente, Long id) {
        Optional<Cliente> clientById = findById(id);
        Cliente clienteAtualizado = verifyNameAndEmailNotNull(cliente, clientById);
        return update(clienteAtualizado);
    }

    private Cliente verifyNameAndEmailNotNull(Cliente cliente, Optional<Cliente> clienteOptional) {
        if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            clienteOptional.get().setNome(cliente.getNome());
        }
        if ( cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
            clienteOptional.get().setEmail(cliente.getEmail());
        }
        return clienteOptional.get();
    }

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
