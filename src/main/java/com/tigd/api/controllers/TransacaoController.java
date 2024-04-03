package com.tigd.api.controllers;

import com.tigd.api.domain.Transacao;
import com.tigd.api.dto.TransacaoDTO;
import com.tigd.api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador para operações relacionadas às transações.
 *
 * Este controlador gerencia operações relacionadas às transações, como processamento de novas transações e listagem
 * de todas as transações.
 *
 * As requisições são mapeadas para o caminho "/transacoes".
 *
 * @author viniciuslemes10
 * @author gemeoslemes
 */
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService service;

    /**
     * Processa uma nova transação.
     *
     * @param transacaoDTO o DTO da transação contendo as informações para processar a transação
     * @param uriComponentsBuilder o construtor de componentes de URI para construir a URI da nova transação
     * @return uma resposta HTTP contendo a transação processada e a URI para acessá-la
     */
    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody TransacaoDTO transacaoDTO,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        Transacao transacao = new Transacao(transacaoDTO);
        service.processarTransacao(transacao);
        URI uri = uriComponentsBuilder.buildAndExpand(transacao).toUri();
        return ResponseEntity.created(uri).body(transacao);
    }

    /**
     * Lista todas as transações cadastradas.
     *
     * @return uma resposta HTTP contendo a lista de todas as transações
     */
    @GetMapping
    public ResponseEntity<List<Transacao>> listAllTransacao() {
        List<Transacao> transacaos = service.findAllTransacoes();
        return ResponseEntity.ok(transacaos);
    }
}
