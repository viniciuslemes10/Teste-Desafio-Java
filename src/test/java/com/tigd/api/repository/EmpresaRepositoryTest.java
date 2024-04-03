package com.tigd.api.repository;

import com.tigd.api.domain.Empresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmpresaRepositoryTest {

    @Autowired
    EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Buscando empresa ativa")
    void findByEmailCase1() {
        Empresa empresa = new Empresa(1L, "testeEmpresa", "33.494.022/0001-04",
                "testeempresa@tgid.com", new BigDecimal(1000),
                new BigDecimal(0.02), true);

        Empresa empresaPersistida = this.createEmpresa(empresa);

        Empresa buscaEmpresaAtiva = this.empresaRepository
                .findByEmail(empresaPersistida.getId(), empresaPersistida.isAtivo());

        assertThat(buscaEmpresaAtiva.isAtivo()).isTrue();
    }

    @Test
    @DisplayName("Buscando empresa inativa")
    void findByEmailCase2() {
        Empresa empresa = new Empresa(1L, "testeEmpresa", "33.494.022/0001-04",
                "testeempresa@tgid.com", new BigDecimal(1000),
                new BigDecimal(0.02), false);

        Empresa empresaPersistida = this.createEmpresa(empresa);

        Empresa buscaEmpresaAtiva = this.empresaRepository
                .findByEmail(empresaPersistida.getId(), empresaPersistida.isAtivo());

        assertThat(buscaEmpresaAtiva.isAtivo()).isFalse();
    }

    @Test
    @DisplayName("Buscando empresa ativa pelo CNPJ")
    void findByCnpjCase1() {
        Empresa empresa = new Empresa(1L, "testeEmpresa", "33.494.022/0001-04",
                "testeempresa@tgid.com", new BigDecimal(1000),
                new BigDecimal(0.02), true);

        Empresa empresaPersistida = this.createEmpresa(empresa);

        Empresa buscaDeEmpresaAtivaPeloCnpj = this.empresaRepository
                .findByCnpj(empresaPersistida.getCnpj(), empresaPersistida.isAtivo());

        assertThat(buscaDeEmpresaAtivaPeloCnpj).isNotNull();
        assertThat(buscaDeEmpresaAtivaPeloCnpj.isAtivo()).isTrue();
    }

    @Test
    @DisplayName("Buscando empresa inativa pelo CNPJ")
    void findByCnpjCase2() {
        Empresa empresa = new Empresa(1L, "testeEmpresa", "33.494.022/0001-04",
                "testeempresa@tgid.com", new BigDecimal(1000),
                new BigDecimal(0.02), false);

        Empresa empresaPersistida = this.createEmpresa(empresa);

        Empresa buscaDeEmpresaAtivaPeloCnpj = this.empresaRepository
                .findByCnpj(empresaPersistida.getCnpj(), empresaPersistida.isAtivo());

        assertThat(buscaDeEmpresaAtivaPeloCnpj).isNotNull();
        assertThat(buscaDeEmpresaAtivaPeloCnpj.isAtivo()).isFalse();
    }

    @Test
    @DisplayName("Buscando empresa pelo CNPJ que não existe na DB")
    void findByCnpjCase3() {
        String document = "95.235.731/0001-22";
        Empresa buscandoEmpresaPeloCnpj = this.empresaRepository.findByCnpj(document, true);
        assertThat(buscandoEmpresaPeloCnpj).isNull();
    }

    private Empresa createEmpresa(Empresa empresa) {
        return this.empresaRepository.save(empresa);
    }
}