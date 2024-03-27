package com.tigd.api.domain;

import com.tigd.api.dto.ClienteDTO;
import com.tigd.api.dto.ClienteUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.Set;

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

    public Cliente(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome().trim().replaceAll("\\s+", " ");
        this.cpf = clienteDTO.cpf().trim().replaceAll("\\s+", "");;
        this.email = clienteDTO.email().trim().replaceAll("\\s+", "");;
        this.saldo = clienteDTO.saldo();
        this.ativo = true;
    }

    public Cliente(ClienteUpdateDTO clienteUpdateDTO) {
        this.nome = clienteUpdateDTO.nome().trim();
        this.email = clienteUpdateDTO.email().trim();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
