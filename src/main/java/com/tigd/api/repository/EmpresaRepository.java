package com.tigd.api.repository;

import com.tigd.api.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Interface para acesso aos dados das empresas.
 *
 * Esta interface define métodos para acessar e manipular dados relacionados às empresas no banco de dados.
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    /**
     * Retorna uma empresa pelo ID e pelo status de ativação.
     *
     * @param id o ID da empresa a ser encontrado
     * @param ativo o status de ativação da empresa
     * @return a empresa correspondente aos critérios fornecidos, ou null se nenhuma empresa for encontrada
     */
    @Query("SELECT e FROM Empresa e WHERE e.id = :id AND e.ativo = :ativo")
    Empresa findByEmail(Long id, boolean ativo);

    /**
     * Retorna uma empresa pelo CNPJ e pelo status de ativação.
     *
     * @param cnpj o CNPJ da empresa a ser encontrado
     * @param ativo o status de ativação da empresa
     * @return a empresa correspondente aos critérios fornecidos, ou null se nenhuma empresa for encontrada
     */
    @Query("SELECT e FROM Empresa e WHERE e.cnpj = :cnpj AND e.ativo = :ativo")
    Empresa findByCnpj(String cnpj, boolean ativo);

    /**
     * Verifica se uma empresa existe pelo email.
     *
     * @param email o email da empresa a ser verificado
     * @return true se a empresa existir pelo email fornecido, false caso contrário
     */
    boolean existsByEmail(String email);
}
