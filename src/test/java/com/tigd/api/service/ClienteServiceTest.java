package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.exceptions.CpfUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.validators.DocumentValidator;
import com.tigd.api.validators.ValidadorAtualizadorEntidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DocumentValidator documentValidator;

    @Mock
    private ValidadorAtualizadorEntidade atualizadorEntidade;

    @Autowired
    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Sucesso ao salvar um Cliente.")
    void saveCase1() {
        Cliente cliente = createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        mockDocumentValidator(cliente);
        cliente.setCpf("37524317093");
        verifySaveAndFindMethods(cliente);
    }

    @Test
    @DisplayName("Lançando exceção(CpfUniqueException) caso CPF já esteja cadastrado no DB.")
    void saveCase2() {
        Cliente cliente = createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        mockDocumentValidator(cliente);
        cliente.setCpf("37524317093");
        verifySaveAndFindMethods(cliente);

        Cliente cliente2 = createCliente(2L, "viniciuslemes10", "375.243.170-93",
                "vinicius@tgid.com", new BigDecimal(1000), true);
        mockDocumentValidator(cliente2);
        simulateFindByCpf(cliente2);

        CpfUniqueException exception = new CpfUniqueException();
        assertThrowsException(exception, cliente2);
        verifySaveNeverCalled(cliente2);
    }

    @Test
    @DisplayName("Lançando exceção(EmailUniqueException) caso email já esteja cadastrado no DB.")
    void saveCase3() {
        Cliente cliente = createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        mockDocumentValidator(cliente);
        cliente.setCpf("37524317093");
        verifySaveAndFindMethods(cliente);

        Cliente cliente2 = createCliente(2L, "viniciuslemes10", "570.599.380-36",
                "gemeos@tgid.com", new BigDecimal(1000), true);
        mockDocumentValidator(cliente2);
        cliente2.setCpf("57059938036");
        simulateFindByEmial(cliente2);

        EmailUniqueException exception = new EmailUniqueException();
        assertThrowsException(exception, cliente2);
        verifySaveNeverCalled(cliente2);
    }

    private Cliente createCliente(Long id, String username, String cpf, String email, BigDecimal saldo, boolean status) {
        return new Cliente(id, username, cpf, email, saldo, status);
    }

    private void mockDocumentValidator(Cliente cliente) {
        String cpf = cliente.getCpf().replaceAll("[.-]", "");
        when(documentValidator.isValid(cliente.getCpf(), "cliente")).thenReturn(cpf);
    }

    private void simulateFindByCpf(Cliente cliente) {
        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(cliente);
    }

    private void simulateFindByEmial(Cliente cliente) {
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(cliente);
    }

    private void assertThrowsException(Exception exception, Cliente cliente) {
        Assertions.assertThrows(exception.getClass(), () -> {
            if(exception instanceof CpfUniqueException) {
                clienteService.isCpfPresent(cliente);
            } else if (exception instanceof EmailUniqueException) {
                clienteService.isEmailPresent(cliente);
            }
        });
    }

    private void verifySaveNeverCalled(Cliente cliente) {
        verify(clienteRepository, never()).save(cliente);
    }

    private void verifySaveAndFindMethods(Cliente cliente) {
        clienteService.isCpfPresent(cliente);
        verifyFindCpfClient(cliente);
        clienteService.isEmailPresent(cliente);
        verifyFindEmailClient(cliente);
        clienteService.save(cliente);
        verifySaveClient(cliente);
    }

    private void verifySaveClient(Cliente cliente) {
        verify(clienteRepository, times(1)).save(cliente);
    }

    private void verifyFindEmailClient(Cliente cliente) {
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
    }

    private void verifyFindCpfClient(Cliente cliente) {
        verify(clienteRepository, times(1)).findByCpf(cliente.getCpf());
    }
}