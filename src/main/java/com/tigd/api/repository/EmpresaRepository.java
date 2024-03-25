package com.tigd.api.repository;

import com.tigd.api.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findByEmail(String email);

    Empresa findByCnpj(String cnpj);
}
