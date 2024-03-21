package com.tigd.api.service;

import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        String cpfTruncado = cpfTruncado(cliente);
        cliente.setCpf(cpfTruncado);
        return clienteRepository.save(cliente);
    }

    private String cpfTruncado(Cliente cliente) {
        String cpf = cliente.getCpf();
        if(cpf.length() == 14) {
            String cpfTruncado = cpf.replaceAll("[.-]", "");
            return cpfTruncado;
        }
        return cpf;
    }
}
