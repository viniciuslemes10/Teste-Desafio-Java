package com.tigd.api.service;

import com.tigd.api.domain.Empresa;
import com.tigd.api.exceptions.CnpjUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.repository.EmpresaRepository;
import com.tigd.api.validators.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DocumentValidator documentValidator;

    public List<Empresa> findAllEmpresa() {
        return empresaRepository.findAll();
    }

    public Empresa save(Empresa empresa) {
        String documentCnpj = documentValidator.isValid(empresa.getCnpj(), "empresa");
        empresa.setCnpj(documentCnpj);
        isPresentEmail(empresa);
        isPresentCnpj(empresa);
        return empresaRepository.save(empresa);
    }

    public Empresa atualizarSaldo(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    private void isPresentCnpj(Empresa empresa) {
        boolean empresas = findByCnpj(empresa);

        if (empresas) {
            throw new CnpjUniqueException();
        }

    }

    private boolean findByCnpj(Empresa empresa) {
        Empresa empresas = empresaRepository.findByCnpj(empresa.getCnpj());
        return empresas != null;
    }

    private void isPresentEmail(Empresa empresa) {
        boolean empresaEmail = findByEmail(empresa);

        if(empresaEmail) {
            throw new EmailUniqueException();
        }
    }

    private boolean findByEmail(Empresa empresa) {
        Empresa empresaEmail = empresaRepository.findByEmail(empresa.getEmail());
        return empresaEmail != null;
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa update(Empresa empresa, Long id) {
        Optional<Empresa> empresaById = empresaRepository.findById(id);
        Empresa updateCompany = verifyNameAndEmailAndRateSystemNotNull(empresa, empresaById);
        return empresaRepository.save(updateCompany);
    }

    private Empresa verifyNameAndEmailAndRateSystemNotNull(Empresa empresa, Optional<Empresa> empresaOptional) {
        if(empresa.getNome() != null && !empresa.getNome().isEmpty()){
            empresaOptional.get().setNome(empresa.getNome());
        }if(empresa.getEmail() != null && !empresa.getEmail().isEmpty()){
            empresaOptional.get().setEmail(empresa.getEmail());
        }if(empresa.getTaxaSistema() != null){
            empresaOptional.get().setTaxaSistema(empresa.getTaxaSistema());
        }
        return empresaOptional.get();
    }
}
