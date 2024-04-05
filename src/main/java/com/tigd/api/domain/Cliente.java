package com.tigd.api.domain;

import com.tigd.api.dto.ClienteDTO;
import com.tigd.api.dto.ClienteUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

/**
 * Classe que representa um cliente.
 *
 * Esta classe representa um cliente da aplicação, contendo informações como nome, CPF, email e saldo.
 *
 * @author viniciuslemes10
 * @author gemeoslemes
 */
@Entity()
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @CPF(message = "CPF inválido")
    @Column(name = "cpf", unique = true)
    private String cpf;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "ativo")
    private boolean ativo;

    /**
     * Construtor para criar um objeto Cliente a partir de um objeto ClienteDTO.
     *
     * @param clienteDTO o DTO do cliente contendo informações para inicialização
     */
    public Cliente(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome().trim().replaceAll("\\s+", " ");
        this.cpf = clienteDTO.cpf().trim().replaceAll("\\s+", "");;
        this.email = clienteDTO.email().trim().replaceAll("\\s+", "");;
        this.saldo = clienteDTO.saldo();
        this.ativo = true;
    }

//    /**
//     * Construtor para criar um objeto Cliente a partir de um objeto ClienteUpdateDTO.
//     *
//     * @param clienteUpdateDTO o DTO de atualização do cliente contendo informações para inicialização
//     */
//    public Cliente(ClienteUpdateDTO clienteUpdateDTO) {
//        this.nome = clienteUpdateDTO.nome().trim();
//        this.email = clienteUpdateDTO.email().trim();
//    }

    /**
     * Retorna uma representação em forma de string do objeto Cliente.
     *
     * @return uma string representando o objeto Cliente
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", saldo=" + saldo +
                ", ativo=" + ativo +
                '}';
    }
}
