package com.tigd.api.controllers;

import com.tigd.api.domain.Cliente;
import com.tigd.api.domain.Empresa;
import com.tigd.api.domain.Transacao;
import com.tigd.api.dto.ClienteDTO;
import com.tigd.api.dto.TransacaoDTO;
import com.tigd.api.service.ClienteService;
import com.tigd.api.service.EmpresaService;
import com.tigd.api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody TransacaoDTO transacaoDTO,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        Transacao transacao = new Transacao(transacaoDTO);
        service.processarTransacao(transacao);
        URI uri = uriComponentsBuilder.buildAndExpand(transacao).toUri();
        return ResponseEntity.created(uri).body(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listAllTransacao() {
        List<Transacao> transacaos = service.findAllTransacoes();
        return ResponseEntity.ok(transacaos);
    }
}
