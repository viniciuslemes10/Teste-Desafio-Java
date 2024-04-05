package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.exceptions.CpfUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.validators.DocumentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ActiveProfiles("test")
class ClienteSaveServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DocumentValidator documentValidator;

    @Autowired
    @InjectMocks
    private ClienteService clienteService;

    private TestAuxiliaresClienteService testAuxiliaresClienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testAuxiliaresClienteService = new TestAuxiliaresClienteService(clienteRepository, documentValidator, clienteService);
    }

    @Test
    @DisplayName("Sucesso ao salvar um Cliente.")
    void saveCase1() {
        Cliente cliente = testAuxiliaresClienteService.createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente);
        testAuxiliaresClienteService.verifySaveAndFindMethods(cliente);
    }

    @Test
    @DisplayName("Lançando exceção(CpfUniqueException) caso CPF já esteja cadastrado no DB.")
    void saveCase2() {
        Cliente cliente = testAuxiliaresClienteService.createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente);
        testAuxiliaresClienteService.verifySaveAndFindMethods(cliente);

        Cliente cliente2 = testAuxiliaresClienteService.createCliente(2L, "viniciuslemes10", "375.243.170-93",
                "vinicius@tgid.com", new BigDecimal(1000), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente2);
        testAuxiliaresClienteService.simulateFindByCpf(cliente2);

        CpfUniqueException exception = new CpfUniqueException();
        testAuxiliaresClienteService.assertThrowsException(exception, cliente2);
        testAuxiliaresClienteService.verifySaveNeverCalled(cliente2);
    }

    @Test
    @DisplayName("Lançando exceção(EmailUniqueException) caso email já esteja cadastrado no DB.")
    void saveCase3() {
        Cliente cliente = testAuxiliaresClienteService.createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente);

        testAuxiliaresClienteService.verifySaveAndFindMethods(cliente);

        Cliente cliente2 = testAuxiliaresClienteService.createCliente(2L, "viniciuslemes10", "570.599.380-36",
                "gemeos@tgid.com", new BigDecimal(1000), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente2);
        testAuxiliaresClienteService.simulateFindByEmial(cliente2);

        EmailUniqueException exception = new EmailUniqueException();
        testAuxiliaresClienteService.assertThrowsException(exception, cliente2);
        testAuxiliaresClienteService.verifySaveNeverCalled(cliente2);
    }
}