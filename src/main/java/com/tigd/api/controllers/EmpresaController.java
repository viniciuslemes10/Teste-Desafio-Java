package com.tigd.api.controllers;

import com.tigd.api.domain.Empresa;
import com.tigd.api.dto.EmpresaDTO;
import com.tigd.api.dto.EmpresaUpdateDTO;
import com.tigd.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para operações relacionadas às empresas.
 *
 * Este controlador gerencia as operações CRUD (criação, leitura, atualização e exclusão) relacionadas às empresas.
 * Ele lida com requisições HTTP para listar todas as empresas, criar uma nova empresa, atualizar uma empresa existente
 * e inativar uma empresa.
 *
 * As requisições são mapeadas para o caminho "/empresas".
 * @author viniciuslemes10
 * @author gemeoslemes
 */
@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService service;

    /**
     * Lista todas as empresas cadastradas.
     *
     * @return uma resposta HTTP contendo a lista de todas as empresas
     */
    @GetMapping
    public ResponseEntity<List<Empresa>> listFindAllEmpresa() {
        List<Empresa> empresas = service.findAllEmpresa();
        return ResponseEntity.ok(empresas);
    }

    /**
     * Cria uma nova empresa.
     *
     * @param empresaDTO o DTO da empresa contendo as informações para criar a empresa
     * @param uriComponentsBuilder o construtor de componentes de URI para construir a URI da nova empresa
     * @return uma resposta HTTP contendo a empresa criada e a URI para acessá-la
     */
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody EmpresaDTO empresaDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Empresa empresa = new Empresa(empresaDTO);
        Empresa empresaSave = service.save(empresa);
        URI uri = uriComponentsBuilder.buildAndExpand(empresaSave).toUri();

        return ResponseEntity.created(uri).body(empresaSave);
    }

    /**
     * Atualiza uma empresa existente.
     *
     * @param empresaUpdateDTO o DTO de atualização da empresa contendo as informações atualizadas
     * @param id o ID da empresa a ser atualizada
     * @return uma resposta HTTP contendo a empresa atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@RequestBody EmpresaUpdateDTO empresaUpdateDTO, @PathVariable Long id) {
        Empresa empresa = new Empresa(empresaUpdateDTO);
        Empresa update = service.update(empresa, id);
        return ResponseEntity.ok(update);
    }

    /**
     * Inativa uma empresa existente.
     *
     * @param id o ID da empresa a ser inativada
     * @return uma resposta HTTP indicando sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> inactiveEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresaOpitinal = service.buscarEmpresaPorId(id);
        Empresa empresa = service.deleteByIdEmpresa(empresaOpitinal);
        return ResponseEntity.noContent().build();
    }
}
