package com.tigd.api.repository;

import com.tigd.api.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("SELECT e FROM Empresa e WHERE e.id = :id AND e.ativo = :ativo")
    Empresa findByEmail(Long id, boolean ativo);
    @Query("SELECT e FROM Empresa e WHERE e.cnpj = :cnpj AND e.ativo = :ativo")
    Empresa findByCnpj(String cnpj, boolean ativo);

    boolean existsByEmail(String email);
}
