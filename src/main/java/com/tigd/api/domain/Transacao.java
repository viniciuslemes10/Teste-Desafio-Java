package com.tigd.api.domain;

import com.tigd.api.dto.TransacaoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe que representa uma transação.
 *
 * Esta classe representa uma transação realizada entre um cliente e uma empresa na aplicação.
 *
 * @author viniciuslemes10
 * @author gemeoslemes
 */
@Entity
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private LocalDateTime data;

    @Column(name = "tipo")
    private char tipo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    /**
     * Construtor para criar um objeto Transacao a partir de um objeto TransacaoDTO.
     *
     * @param transacaoDTO o DTO da transação contendo informações para inicialização
     */
    public Transacao(TransacaoDTO transacaoDTO) {
        this.data = LocalDateTime.now();
        this.valor = transacaoDTO.valor();
        this.tipo = transacaoDTO.tipo();
        this.cliente = new Cliente();
        this.cliente.setId(transacaoDTO.cliente());
        this.empresa = new Empresa();
        this.empresa.setId(transacaoDTO.empresa());
    }

    /**
     * Retorna uma representação em forma de string do objeto Transacao.
     *
     * @return uma string representando o objeto Transacao
     */
    @Override
    public String toString() {
        return "Transacao{" +
                "valor=" + valor +
                ", data=" + data +
                ", tipo=" + tipo +
                ", cliente=" + cliente +
                ", empresa=" + empresa +
                '}';
    }
}
