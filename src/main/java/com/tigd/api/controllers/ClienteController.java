package com.tigd.api.controllers;

import com.tigd.api.dto.ClienteDTO;
import com.tigd.api.dto.ClienteUpdateDTO;
import com.tigd.api.service.ClienteService;
import com.tigd.api.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para operações relacionadas aos clientes.
 *
 * Este controlador gerencia as operações CRUD (criação, leitura, atualização e exclusão) relacionadas aos clientes.
 * Ele lida com requisições HTTP para listar todos os clientes, criar um novo cliente, atualizar um cliente existente
 * e inativar um cliente.
 *
 * As requisições são mapeadas para o caminho "/clientes".
 *
 * @author gemeoslemes
 * @author viniciuslemes10
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return uma resposta HTTP contendo a lista de todos os clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listAllClient() {
        List<Cliente> cliente = service.findAllClients();
        return ResponseEntity.ok(cliente);
    }

    /**
     * Cria um novo cliente.
     *
     * @param clienteDTO o DTO do cliente contendo as informações para criar o cliente
     * @param uriComponentsBuilder o construtor de componentes de URI para construir a URI do novo cliente
     * @return uma resposta HTTP contendo o cliente criado e a URI para acessá-lo
     */
    @PostMapping
    public ResponseEntity<Cliente> createClient(@RequestBody ClienteDTO clienteDTO, UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = new Cliente(clienteDTO);
        Cliente clienteSave = service.save(cliente);
        URI uri = uriComponentsBuilder.buildAndExpand(clienteSave).toUri();
        return ResponseEntity.created(uri).body(clienteSave);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param clienteUpdateDTO o DTO de atualização do cliente contendo as informações atualizadas
     * @param id o ID do cliente a ser atualizado
     * @return uma resposta HTTP contendo o cliente atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateClient(@RequestBody ClienteUpdateDTO clienteUpdateDTO, @PathVariable Long id) {
//        Cliente cliente = new Cliente(clienteUpdateDTO);
        Cliente updateClient = service.updateClient(clienteUpdateDTO, id);
        return ResponseEntity.ok(updateClient);
    }

    /**
     * Inativa um cliente existente.
     *
     * @param id o ID do cliente a ser inativado
     * @return uma resposta HTTP indicando sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> inactiveClient(@PathVariable Long id) {
        Optional<Cliente> cliente = service.findById(id);
        Cliente clientInactive = service.deleteClientById(cliente);
        return ResponseEntity.noContent().build();
    }
}
