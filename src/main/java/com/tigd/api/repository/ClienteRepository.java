package com.tigd.api.repository;

import com.tigd.api.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String email);

    Cliente findByCpf(String cpf);

    @Query("SELECT c FROM Cliente c WHERE id = :id AND ativo = :ativo")
    Cliente findByAtivo(Long id, boolean ativo);
}
