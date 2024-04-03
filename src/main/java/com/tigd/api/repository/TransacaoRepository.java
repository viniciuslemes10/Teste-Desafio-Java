package com.tigd.api.repository;

import com.tigd.api.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface para acesso aos dados das transações.
 *
 * Esta interface estende JpaRepository para acessar e manipular dados relacionados às transações no banco de dados.
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
