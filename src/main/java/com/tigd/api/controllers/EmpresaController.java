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

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService service;

    @GetMapping
    public ResponseEntity<List<Empresa>> listFindAllEmpresa() {
        List<Empresa> empresas = service.findAllEmpresa();
        return ResponseEntity.ok(empresas);
    }

    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody EmpresaDTO empresaDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Empresa empresa = new Empresa(empresaDTO);
        Empresa empresaSave = service.save(empresa);
        URI uri = uriComponentsBuilder.buildAndExpand(empresaSave).toUri();

        return ResponseEntity.created(uri).body(empresaSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@RequestBody EmpresaUpdateDTO empresaUpdateDTO, @PathVariable Long id) {
        Empresa empresa = new Empresa(empresaUpdateDTO);
        Empresa update = service.update(empresa, id);
        return ResponseEntity.ok(update);
    }
}
