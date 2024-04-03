package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.exceptions.CpfUniqueException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Cliente cliente = new Cliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);

        when(documentValidator.isValid(cliente.getCpf(),"cliente")).thenReturn("37524317093");

        cliente.setCpf("37524317093");

        clienteService.isCpfPresent(cliente);
        verify(clienteRepository, times(1)).findByCpf(cliente.getCpf());

        clienteService.isEmailPresent(cliente);
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());

        clienteService.save(cliente);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Lançando exceção(CpfUniqueException) caso CPF já esteja cadastrado no DB.")
    void saveCase2() {
        Cliente cliente = new Cliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);

        when(documentValidator.isValid(cliente.getCpf(),"cliente")).thenReturn("37524317093");

        cliente.setCpf("37524317093");

        clienteService.isCpfPresent(cliente);
        verify(clienteRepository, times(1)).findByCpf(cliente.getCpf());

        clienteService.isEmailPresent(cliente);
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());

        clienteService.save(cliente);
        verify(clienteRepository, times(1)).save(cliente);

        Cliente cliente2 = new Cliente(2L, "viniciuslemes10", "375.243.170-93",
                "vinicius@tgid.com", new BigDecimal(1000), true);

        when(documentValidator.isValid(cliente2.getCpf(),"cliente")).thenReturn("37524317093");
        when(clienteRepository.findByCpf(cliente2.getCpf())).thenReturn(cliente2);

        CpfUniqueException exception = Assertions.assertThrows(CpfUniqueException.class, () -> {
            clienteService.isCpfPresent(cliente2);
        });

        verify(clienteRepository, never()).save(cliente2);
    }
    @Test
    @DisplayName("Lançando exceção(EmailUniqueException) caso email já esteja cadastrado no DB.")
    void saveCase3() {
    }
    @Test
    @DisplayName("Lançando exceção(CpfIllegalArgException) para CPF inválido.")
    void saveCase4() {
    }
}