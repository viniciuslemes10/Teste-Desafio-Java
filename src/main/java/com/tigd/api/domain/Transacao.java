package com.tigd.api.domain;

import com.tigd.api.dto.TransacaoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public Transacao(TransacaoDTO transacaoDTO) {
        this.data = LocalDateTime.now();
        this.valor = transacaoDTO.valor();
        this.tipo = transacaoDTO.tipo();
        this.cliente = new Cliente();
        this.cliente.setId(transacaoDTO.cliente());
        this.empresa = new Empresa();
        this.empresa.setId(transacaoDTO.empresa());
    }

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
