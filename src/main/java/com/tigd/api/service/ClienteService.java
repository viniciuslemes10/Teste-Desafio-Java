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
}
