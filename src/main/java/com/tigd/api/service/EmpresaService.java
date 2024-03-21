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

    public Empresa save(Empresa empresa) {
        String cnpj = cnpjTruncado(empresa);
        empresa.setCnpj(cnpj);
        return empresaRepository.save(empresa);
    }

    private String cnpjTruncado(Empresa empresa) {
        String cnpj = empresa.getCnpj();
        if(cnpj.length() == 18) {
            String cnpjTruncado = cnpj.replaceAll("[./-]", "");
            return cnpjTruncado;
        }
        return cnpj;
    }
}
