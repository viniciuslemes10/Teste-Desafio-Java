package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.exceptions.CpfUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.validators.DocumentValidator;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TestAuxiliaresClienteService {
    private final ClienteRepository clienteRepository;
    private final DocumentValidator documentValidator;
    private final ClienteService clienteService;

    public TestAuxiliaresClienteService(ClienteRepository clienteRepository, DocumentValidator documentValidator, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.documentValidator = documentValidator;
        this.clienteService = clienteService;
    }

    protected void verifySaveNeverCalled(Cliente cliente) {
        verify(clienteRepository, never()).save(cliente);
    }

    protected void verifySaveAndFindMethods(Cliente cliente) {
        clienteService.isCpfPresent(cliente);
        verifyFindCpfClient(cliente);
        clienteService.isEmailPresent(cliente);
        verifyFindEmailClient(cliente);
        clienteService.save(cliente);
        verifySaveClient(cliente);
    }

    protected Cliente createCliente(Long id, String username, String cpf, String email, BigDecimal saldo, boolean status) {
        return new Cliente(id, username, cpf, email, saldo, status);
    }

    protected void mockDocumentValidator(Cliente cliente) {
        String cpf = cliente.getCpf().replaceAll("[.-]", "");
        when(documentValidator.isValid(cliente.getCpf(), "cliente")).thenReturn(cpf);
    }

    protected void simulateFindByCpf(Cliente cliente) {
        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(cliente);
    }

    protected void simulateFindByEmial(Cliente cliente) {
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(cliente);
    }

    protected void assertThrowsException(Exception exception, Cliente cliente) {
        Assertions.assertThrows(exception.getClass(), () -> {
            if(exception instanceof CpfUniqueException) {
                clienteService.isCpfPresent(cliente);
            } else if (exception instanceof EmailUniqueException) {
                clienteService.isEmailPresent(cliente);
            }
        });
    }

    protected void verifySaveClient(Cliente cliente) {
        verify(clienteRepository, times(1)).save(cliente);
    }

    protected void verifyFindEmailClient(Cliente cliente) {
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
    }

    protected void verifyFindCpfClient(Cliente cliente) {
        verify(clienteRepository, times(1)).findByCpf(cliente.getCpf());
    }
}
