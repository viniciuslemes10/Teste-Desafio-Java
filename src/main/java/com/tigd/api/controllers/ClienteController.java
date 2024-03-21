package com.tigd.api.controllers;

import com.tigd.api.service.ClienteService;
import com.tigd.api.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
