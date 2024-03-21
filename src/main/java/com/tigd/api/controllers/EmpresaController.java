package com.tigd.api.controllers;

import com.tigd.api.domain.Empresa;
import com.tigd.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
