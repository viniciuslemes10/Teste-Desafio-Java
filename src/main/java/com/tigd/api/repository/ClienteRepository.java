package com.tigd.api.repository;

import com.tigd.api.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface para acesso aos dados dos clientes.
 *
 * Esta interface define métodos para acessar e manipular dados relacionados aos clientes no banco de dados.
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Retorna um cliente pelo email.
     *
     * @param email o email do cliente a ser encontrado
     * @return o cliente correspondente ao email fornecido, ou null se nenhum cliente for encontrado
     */
    Cliente findByEmail(String email);

    /**
     * Retorna um cliente pelo CPF.
     *
     * @param cpf o CPF do cliente a ser encontrado
     * @return o cliente correspondente ao CPF fornecido, ou null se nenhum cliente for encontrado
     */
    Cliente findByCpf(String cpf);

    /**
     * Retorna um cliente pelo ID e pelo status de ativação.
     *
     * @param id o ID do cliente a ser encontrado
     * @param ativo o status de ativação do cliente
     * @return o cliente correspondente aos critérios fornecidos, ou null se nenhum cliente for encontrado
     */
    @Query("SELECT c FROM Cliente c WHERE id = :id AND ativo = :ativo")
    Cliente findByAtivo(Long id, boolean ativo);
}
