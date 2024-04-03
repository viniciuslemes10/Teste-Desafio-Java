package com.tigd.api.service;

import com.tigd.api.domain.Empresa;
import com.tigd.api.exceptions.CnpjUniqueException;
import com.tigd.api.exceptions.ContaInativaException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.exceptions.EmpresaNotFoundException;
import com.tigd.api.repository.EmpresaRepository;
import com.tigd.api.validators.ValidadorAtualizadorEntidade;
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

    @Autowired
    private ValidadorAtualizadorEntidade atualizadorEntidade;

    /**
     * <p>Método <b>findAllEmpresa()</b> para listar todas as empresas
     * cadastradas na base de dados.</p>
     *
     * @return Lista de empresas cadastradas.
     **/
    public List<Empresa> findAllEmpresa() {
        return empresaRepository.findAll();
    }

    /**
     * Salva uma nova empresa no banco de dados e executa validações de documentos e e-mails.
     *
     * @param empresa a empresa a ser salva.
     * @return a empresa salva no banco de dados.
     *
     * <p>Este método executa as seguintes operações:</p>
     * <ul>
     *   <li>Valida o CNPJ da empresa usando o {@link #documentValidator documentValidator.isValid()}.</li>
     *   <li>Verifica se o e-mail da empresa já está presente no banco de dados usando {@link #verificarExistenciaEmailNaBaseDeDados(Empresa)}.</li>
     *   <li>Verifica se o CNPJ da empresa já está presente no banco de dados usando {@link #isPresentCnpj(Empresa)}.</li>
     * </ul>
     */
    public Empresa save(Empresa empresa) {
        String documentCnpj = documentValidator.isValid(empresa.getCnpj(), "empresa");
        empresa.setCnpj(documentCnpj);
        verificarExistenciaEmailNaBaseDeDados(empresa);
        isPresentCnpj(empresa);
        return empresaRepository.save(empresa);
    }

    /**
     * @param empresa Empresa empresa
     *                <p>Método <b>atualizaSaldo()</b> este método atualiza o saldo da empresa ao realizar uma transação.</p>
     * @return Saldo atualizado.
     **/
    public Empresa salvarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * Verifica se o CNPJ da empresa já está presente na base de dados.
     *
     * @param empresa a empresa para verificar se o CNPJ já está cadastrado.
     * @throws CnpjUniqueException se o CNPJ já estiver cadastrado na base de dados.
     *                             <p>Este método chama o método {@link #findByCnpj(Empresa)} para verificar se o CNPJ já está cadastrado.
     *                             Se estiver, uma exceção {@link CnpjUniqueException} é lançada.</p>
     */
    private void isPresentCnpj(Empresa empresa) {
        boolean empresas = findByCnpj(empresa);
        if (empresas) {
            throw new CnpjUniqueException();
        }
    }

    /**
     * @param empresa Empresa empresa.
     *                <p>Método <b>findByCnpj()</b> verifica na base de dados se já existe o cnpj passado e se está ativo.</p>
     * @return Caso esteja ativo ele retorna true se não ele lança uma exception.
     * @throws ContaInativaException Mostrando o status e a mensagem.
     */
    private boolean findByCnpj(Empresa empresa) {
        Empresa empresas = empresaRepository.findByCnpj(empresa.getCnpj(), empresa.isAtivo());
        if (!empresas.isAtivo()) {
            throw new ContaInativaException();
        }
        return true;
    }

    /**
     * @param empresa Empresa empresa
     *                <p>Método <b>isPresentEmail()</b> chama o método {@link #verificarEmailAtivoDaEmpresa(Empresa)}
     *                verificando se o email já está cadastrado e se está ativo na base de dados.</p>
     *                <br>
     *                <pre> {@code
     *                                        if (empresasEmail) {
     *                                                  throw new EmailUniqueException();
     *                                        }
     *                               }
     *
     *                               </pre>
     * @throws EmailUniqueException Mostrando o status e a mensagem.
     **/
    private void verificarExistenciaEmailNaBaseDeDados(Empresa empresa) {
        boolean emailExistente = empresaRepository.existsByEmail(empresa.getEmail());
        if (emailExistente) {
            throw new EmailUniqueException();
        }
    }

    /**
     * Verifica na base de dados se o email passado já está cadastrado e se a conta está ativa.
     *
     * @param empresa a empresa contendo o email a ser verificado.
     * @return Se o email estiver presente e a conta
     * estiver ativa, o método retorna true. Caso contrário, uma exceção {@link ContaInativaException} é lançada.
     * @throws ContaInativaException se a conta associada ao email estiver inativa.
     *
     *                               <p>Este método chama o método {@link EmpresaRepository#findByEmail(Long, boolean)} para verificar se o email
     *                               já está cadastrado na base de dados e se a conta associada está ativa.</p>
     */
    private boolean verificarEmailAtivoDaEmpresa(Empresa empresa) {
        Empresa empresaEmail = empresaRepository.findByEmail(empresa.getId(), empresa.isAtivo());
        if (!empresaEmail.isAtivo()) {
            throw new ContaInativaException();
        }
        return true;
    }

    /**
     * @param id Long id
     *           <p>Método <b>findById()</b> busca na base dados o id passado como parâmetro retornando a empresa.
     *           Caso não encontre lança uma Exception.</p>
     * @return empresa.
     * @throws EmpresaNotFoundException mostrando o status e a mensagem.
     **/
    public Optional<Empresa> buscarEmpresaPorId(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            return empresa;
        } else {
            throw new EmpresaNotFoundException();
        }
    }

    /**
     * Atualiza uma empresa na base de dados com base no ID fornecido.
     *
     * @param empresa a empresa com os novos dados a serem atualizados.
     * @param id o ID da empresa a ser atualizada.
     * @return a empresa atualizada após ser salva na base de dados.
     * @throws EmpresaNotFoundException se a empresa com o ID fornecido não for encontrada na base de dados.
     * @throws ContaInativaException se a conta da empresa não estiver ativa.
     * @throws EmailUniqueException se o email da empresa já estiver em uso por outra empresa na base de dados.
     *
     * @see #buscarEmpresaPorId(Long)
     * @see #verificarEmailAtivoDaEmpresa(Empresa)
     * @see #verificarExistenciaEmailNaBaseDeDados(Empresa)
     */
    public Empresa update(Empresa empresa, Long id) {
        Optional<Empresa> empresaById = buscarEmpresaPorId(id);
        verificarEmailAtivoDaEmpresa(empresaById.get());
        verificarExistenciaEmailNaBaseDeDados(empresa);
        Empresa updateCompany = atualizadorEntidade.verifyNameAndEmailAndRateSystemNotNull(empresa, empresaById);

        return empresaRepository.save(updateCompany);
    }

    /**
     * <p>Método <b>deleteByIdEmpresa()</b> muda o estado de ativo para <b>false</b>
     * e chama o método {@link #save(Empresa) save()} salvando na base de dados.</p>
     *
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