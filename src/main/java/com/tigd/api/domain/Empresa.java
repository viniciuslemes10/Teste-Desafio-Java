package com.tigd.api.domain;

import com.tigd.api.dto.EmpresaDTO;
import com.tigd.api.dto.EmpresaUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@Entity()
@Table(name = "empresas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @CNPJ(message = "CNPJ inválido")
    @Column(name = "cnpj", unique = true)
    private String cnpj;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "taxa_sistema")
    private BigDecimal taxaSistema;
    @Column(name = "ativo")
    private boolean ativo;

    public Empresa(EmpresaDTO empresaDTO) {
        this.nome = empresaDTO.nome().trim().replaceAll("\\s+", " ");
        this.cnpj = empresaDTO.cnpj().trim().replaceAll("\\s+", "");
        this.email = empresaDTO.email().trim().replaceAll("\\s+", "");
        this.saldo = empresaDTO.saldo();
        if (empresaDTO.taxaSistema() == null) {
            this.taxaSistema = BigDecimal.valueOf(0.01);
        } else {
            this.taxaSistema = empresaDTO.taxaSistema();
        }
        this.ativo = true;
    }

    public Empresa(EmpresaUpdateDTO empresaUpdateDTO) {
        this.nome = empresaUpdateDTO.nome().trim();
        this.email = empresaUpdateDTO.email().trim();
        this.taxaSistema = empresaUpdateDTO.taxaSistema();
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", saldo=" + saldo +
                ", taxaSistema=" + taxaSistema +
                '}';
    }
}
