package com.tigd.api.service;

import com.tigd.api.domain.Empresa;
import com.tigd.api.exceptions.CnpjUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.exceptions.EmpresaNotFoundException;
import com.tigd.api.repository.EmpresaRepository;
import com.tigd.api.validators.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * @author gemeoslemes, viniciuslemes10<br>
 * <p>Classe <b>EmpresaService</b> para manipulação e inserção de dados na entity empresas.</p>
 **/
@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DocumentValidator documentValidator;

    /**
     * <p>Método <b>findAllEmpresa()</b> para listar todas as empresas
     * cadastradas na base de dados.</p>
     * @return Todas as Empresas cadastradas.
     **/
    public List<Empresa> findAllEmpresa() {
        return empresaRepository.findAll();
    }

    /**
     * @param empresa Empresa empresa
     * <p>Método <b>save()</b> além de cadastrar uma nova empresa na base de dados, ele chama outros métodos
     * como {@link #isPresentEmail(Empresa) isPresentEmail()} e {@link #isPresentCnpj(Empresa) isPresentCnpj()
     * } verificando se o email e cnpj passados já estão cadastrados na base de dados.</p>
     * @return Salvado a empresa.
     **/
    public Empresa save(Empresa empresa) {
        String documentCnpj = documentValidator.isValid(empresa.getCnpj(), "empresa");
        empresa.setCnpj(documentCnpj);
        isPresentEmail(empresa);
        isPresentCnpj(empresa);
        return empresaRepository.save(empresa);
    }
    /**
     * @param empresa Empresa empresa
     * <p>Método <b>atualizaSaldo()</b> este método atualiza o saldo da empresa ao realizar uma transação.</p>
     * @return Saldo atualizado.
     **/
    public Empresa atualizarSaldo(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * @param empresa Empresa empresa
     * <p>Método <b>isPresentCnpj()</b> chama o método <b>findByCnpj()</b>
     * verificando se o CNPJ já está cadastrado na base de dados.</p>
     *            if (empresas) {<br>
     *             throw new CnpjUniqueException();<br>
     *         }
     * @exception CnpjUniqueException Mostrando o status e a mensagem.
     **/
    private void isPresentCnpj(Empresa empresa) {
        boolean empresas = findByCnpj(empresa);

        if (empresas) {
            throw new CnpjUniqueException();
        }

    }

    /**
     * @param empresa Empresa empresa.
     * <p>Método <b>findByCnpj()</b> verifica na base de dados se já existe o cnpj passado.</p>
     * @return Caso for diferente de null o cnpj é retornado.
     */
    private boolean findByCnpj(Empresa empresa) {
        Empresa empresas = empresaRepository.findByCnpj(empresa.getCnpj());
        return empresas != null;
    }

    /**
     * @param empresa Empresa empresa
     * <p>Método <b>isPresentEmail()</b> chama o método <b>findByEmail()</b>
     * verificando se o email já está cadastrado em nossa base de dados.</p>
     *            if (empresasEmail) {<br>
     *             throw new EmailniqueException();<br>
     *         }
     * @exception EmailUniqueException Mostrando o status e a mensagem.
     **/
    private void isPresentEmail(Empresa empresa) {
        boolean empresaEmail = findByEmail(empresa);

        if(empresaEmail) {
            throw new EmailUniqueException();
        }
    }
    /**
     * @param empresa Empresa empresa.
     * <p>Método <b>findByEmail()</b> verifica na base de dados se já existe o email passado.</p>
     * @return Caso for diferente de null o email é retornado.
     */
    private boolean findByEmail(Empresa empresa) {
        Empresa empresaEmail = empresaRepository.findByEmail(empresa.getEmail());
        return empresaEmail != null;
    }
    /**
     * @param id Long id
     * <p>Método <b>findById()</b> busca na base dados o id passado como parâmetro retornando a empresa.
     * Caso não encontre lança uma Exception.</p>
     * @return empresa.
     * @exception EmpresaNotFoundException mostrando o status e a mensagem.
     **/
    public Optional<Empresa> findById(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isPresent()) {
            return empresa;
        } else {
            throw new EmpresaNotFoundException();
        }
    }
    /**
     * @param empresa Empresa empresa
     * @param id Long id
     * <p>Método <b>update()</b> chama o método {@link #findById(Long) findById()} passando o id como parâmetro
     * para buscar na base de dados, chama o método {@link #verifyNameAndEmailAndRateSystemNotNull(Empresa, Optional) verifyNameAndEmailAndRateSystemNotNull()}</p>
     **/
    public Empresa update(Empresa empresa, Long id) {
        Optional<Empresa> empresaById = empresaRepository.findById(id);
        Empresa updateCompany = verifyNameAndEmailAndRateSystemNotNull(empresa, empresaById);
        return empresaRepository.save(updateCompany);
    }
    /**
     * Verifica se o nome, email e taxa do sistema não são nulos e atualiza os valores correspondentes na empresa fornecida.
     *
     * @param empresa a empresa com os valores a serem verificados e atualizados
     * @param empresaOptional o Optional contendo a empresa da base de dados
     * @return a empresa atualizada
     */
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
    /**
     * <p>Método <b>deleteByIdEmpresa()</b> muda o estado de ativo para <b>false</b>
     * e chama o método {@link #save(Empresa) save()} salvando na base de dados.</p>
     * @param empresaOpitinal O Optional contendo na base de dados.
     * @return a empresa desativada(deletada).
     **/
    public Empresa deleteByIdEmpresa(Optional<Empresa> empresaOpitinal) {
        Empresa empresa = empresaOpitinal.get();
        empresa.setAtivo(false);
        empresaRepository.save(empresa);
        return empresa;
    }
}
