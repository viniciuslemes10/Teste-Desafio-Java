package com.tigd.api.service;

import com.tigd.api.domain.Cliente;
import com.tigd.api.dto.ClienteUpdateDTO;
import com.tigd.api.repository.ClienteRepository;
import com.tigd.api.validators.DocumentValidator;
import com.tigd.api.validators.ValidadorAtualizadorEntidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ClienteUpdateServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DocumentValidator documentValidator;

    @Mock
    private ValidadorAtualizadorEntidade atualizadorEntidade;

    @InjectMocks
    private ClienteService clienteService;

    private TestAuxiliaresClienteService testAuxiliaresClienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testAuxiliaresClienteService = new TestAuxiliaresClienteService(clienteRepository, documentValidator, clienteService);
    }

    @Test
    @DisplayName("Atualizando cliente com sucesso")
    void updateClientCase2() {
        Cliente cliente = testAuxiliaresClienteService.createCliente(1L, "gemeoslemes", "375.243.170-93",
                "gemeos@tgid.com", new BigDecimal(100), true);
        testAuxiliaresClienteService.mockDocumentValidator(cliente);
        testAuxiliaresClienteService.verifySaveAndFindMethods(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        ClienteUpdateDTO dto = new ClienteUpdateDTO("Vinicius", "vini@tgid.com");

        Optional<Cliente> byId = clienteService.findById(1L);
        verify(clienteRepository, times(1)).findById(1L);

        Cliente clienteComDadosAtualizados = clienteService.atualizaDados(dto, byId);
        clienteService.validarContaAtiva(clienteComDadosAtualizados);

        clienteService.isEmailPresent(clienteComDadosAtualizados);

        assertThat(clienteComDadosAtualizados).isNotNull();

        assertThat(clienteComDadosAtualizados.getNome()).isEqualTo(dto.nome());

        assertThat(clienteComDadosAtualizados.getEmail()).isEqualTo(dto.email());

        assertThat(clienteComDadosAtualizados.isAtivo()).isTrue();

        testAuxiliaresClienteService.verifySaveClient(clienteComDadosAtualizados);
    }
}
