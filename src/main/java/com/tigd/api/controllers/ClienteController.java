package com.tigd.api.controllers;

import com.tigd.api.dto.ClienteDTO;
import com.tigd.api.dto.ClienteUpdateDTO;
import com.tigd.api.service.ClienteService;
import com.tigd.api.domain.Cliente;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listAllClient() {
        List<Cliente> cliente = service.findAllClients();
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> createClient(@RequestBody ClienteDTO clienteDTO, UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = new Cliente(clienteDTO);
        Cliente clienteSave = service.save(cliente);
        URI uri = uriComponentsBuilder.buildAndExpand(clienteSave).toUri();
        return ResponseEntity.created(uri).body(clienteSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateClient(@RequestBody ClienteUpdateDTO clienteUpdateDTO, @PathVariable Long id) {
        Cliente cliente = new Cliente(clienteUpdateDTO);
        Cliente updateClient = service.updateClient(cliente, id);
        return ResponseEntity.ok(updateClient);
    }
}
