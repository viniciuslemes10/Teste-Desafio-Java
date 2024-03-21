package com.tigd.api.service;

import com.tigd.api.domain.Empresa;
import com.tigd.api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAllEmpresa() {
        return empresaRepository.findAll();
    }
}
