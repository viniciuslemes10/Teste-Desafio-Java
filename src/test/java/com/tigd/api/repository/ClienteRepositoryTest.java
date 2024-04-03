package com.tigd.api.repository;

import com.tigd.api.domain.Cliente;
import com.tigd.api.dto.ClienteDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClienteRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;
    @Test
    @DisplayName("Buscando cliente ativo no DB.")
    void findByAtivoCase1() {
        Cliente newCliente = new Cliente(1L, "Gemeos Lemes", "005.284.860-47", "gemeos@tidg.com", new BigDecimal(100), true);
        Cliente clientePersistido = this.createCliente(newCliente);
        Cliente buscaClienteAtivo = this.clienteRepository.findByAtivo(clientePersistido.getId(), clientePersistido.isAtivo());
        assertThat(buscaClienteAtivo).isNotNull();
    }

    @Test
    @DisplayName("Buscando cliente inativo no DB.")
    void findByAtivoCase2() {
        Cliente newCliente = new Cliente(1L, "Gemeos Lemes", "005.284.860-47", "gemeos@tidg.com", new BigDecimal(100), false);
        Cliente clientePersistido = this.createCliente(newCliente);
        Cliente buscaClienteAtivo = this.clienteRepository.findByAtivo(clientePersistido.getId(), clientePersistido.isAtivo());
        assertThat(buscaClienteAtivo.isAtivo()).isFalse();
    }

    private Cliente createCliente(Cliente newCliente) {
        return this.clienteRepository.save(newCliente);
    }
}