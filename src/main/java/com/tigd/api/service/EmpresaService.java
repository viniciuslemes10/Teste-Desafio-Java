package com.tigd.api.service;

import com.tigd.api.domain.Empresa;
import com.tigd.api.exceptions.CnpjUniqueException;
import com.tigd.api.exceptions.EmailUniqueException;
import com.tigd.api.exceptions.EmpresaNotFoundException;
import com.tigd.api.repository.EmpresaRepository;
import com.tigd.api.validators.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
     *
     * @return Todas as Empresas cadastradas.
     **/
    public List<Empresa> findAllEmpresa() {
        return empresaRepository.findAll();
    }

    /**
     * @param empresa Empresa empresa
     *                <p>Método <b>save()</b> além de cadastrar uma nova empresa na base de dados, ele chama outros métodos
     *                como {@link #isPresentEmail(Empresa) isPresentEmail()} e {@link #isPresentCnpj(Empresa) isPresentCnpj()
     *                } verificando se o email e cnpj passados já estão cadastrados na base de dados.</p>
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
     *                <p>Método <b>atualizaSaldo()</b> este método atualiza o saldo da empresa ao realizar uma transação.</p>
     * @return Saldo atualizado.
     **/
    public Empresa atualizarSaldo(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * @param empresa Empresa empresa
     *                <p>Método <b>isPresentCnpj()</b> chama o método <b>findByCnpj()</b>
     *                verificando se o CNPJ já está cadastrado na base de dados.</p>
     *                if (empresas) {<br>
     *                throw new CnpjUniqueException();<br>
     *                }
     * @throws CnpjUniqueException Mostrando o status e a mensagem.
     **/
    private void isPresentCnpj(Empresa empresa) {
        boolean empresas = findByCnpj(empresa);
        if (empresas) {
            throw new CnpjUniqueException();
        }
    }

    /**
     * @param empresa Empresa empresa.
     *                <p>Método <b>findByCnpj()</b> verifica na base de dados se já existe o cnpj passado e se está ativo.</p>
     * @return Caso for diferente de null o cnpj é retornado.
     */
    private boolean findByCnpj(Empresa empresa) {
        Empresa empresas = empresaRepository.findByCnpj(empresa.getCnpj(), empresa.isAtivo());
        return empresas != null;
    }

    /**
     * @param empresa Empresa empresa
     *                <p>Método <b>isPresentEmail()</b> chama o método <b>findByEmail()</b>
     *                verificando se o email já está cadastrado em nossa base de dados.</p>
     *                if (empresasEmail) {<br>
     *                throw new EmailniqueException();<br>
     *                }
     * @throws EmailUniqueException Mostrando o status e a mensagem.
     **/
    private void isPresentEmail(Empresa empresa) {
        boolean empresaEmail = findByEmail(empresa);
        if (empresaEmail) {
            throw new EmailUniqueException();
        }
    }

    /**
     * @param empresa Empresa empresa.
     *                <p>Método <b>findByEmail()</b> verifica na base de dados se já existe o email passado e se está ativa.</p>
     * @return Caso for diferente de null o email é retornado.
     */
    private boolean findByEmail(Empresa empresa) {
        Empresa empresaEmail = empresaRepository.findByEmail(empresa.getEmail(), empresa.isAtivo());
        return empresaEmail != null;
    }

    /**
     * @param id Long id
     *           <p>Método <b>findById()</b> busca na base dados o id passado como parâmetro retornando a empresa.
     *           Caso não encontre lança uma Exception.</p>
     * @return empresa.
     * @throws EmpresaNotFoundException mostrando o status e a mensagem.
     **/
    public Optional<Empresa> findById(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            return empresa;
        } else {
            throw new EmpresaNotFoundException();
        }
    }

    /**
     * @param empresa Empresa empresa
     * @param id      Long id
     *                <p>Método <b>update()</b> chama o método {@link #findById(Long) findById()} passando o id como parâmetro
     *                para buscar na base de dados, chama o método </p><br>
     * @return Retorna a empresa após ser salva na base de dados.
     * @see #verifyNameAndEmailAndRateSystemNotNull(Empresa, Optional)
     * @see #isPresentEmail(Empresa)
     **/
    public Empresa update(Empresa empresa, Long id) {
        isPresentEmail(empresa);
        Optional<Empresa> empresaById = empresaRepository.findById(id);
        Empresa updateCompany = verifyNameAndEmailAndRateSystemNotNull(empresa, empresaById);
        return empresaRepository.save(updateCompany);
    }

    /**
     * Verifica se os campos nome, email e taxa do sistema da empresa não são nulos.
     * Se uma instância de empresa estiver presente no Optional, os campos serão atualizados
     * com os valores fornecidos, desde que não sejam nulos.
     *
     * @param empresa         a empresa contendo os valores a serem verificados e, possivelmente, atualizados.
     * @param empresaOptional um Optional que pode conter uma instância existente de empresa.
     * @return a instância de empresa presente no Optional, se existir.
     * @see #setValueIfNotNullOrEmpty(String, Consumer)
     * @see #setValueIfNotNull(BigDecimal, Consumer)
     */
    private Empresa verifyNameAndEmailAndRateSystemNotNull(Empresa empresa, Optional<Empresa> empresaOptional) {
        empresaOptional.ifPresent(empresaExistente -> {
            setValueIfNotNullOrEmpty(empresa.getNome(), empresaExistente::setNome);
            setValueIfNotNullOrEmpty(empresa.getEmail(), empresaExistente::setEmail);
            setValueIfNotNull(empresa.getTaxaSistema(), empresaExistente::setTaxaSistema);
        });
        return empresaOptional.get();
    }

    /**
     * Verifica se o valor não é nulo e nem vazio, caso ele não for nenhum dos dois o Consumer aceita o argumento,
     * se  ele não vai ser atualizado(aceitado).
     *
     * @param value  O valor a ser vericado.
     * @param setter caso não venha nulo ou vazio ele aceita o argumento.
     */
    private void setValueIfNotNullOrEmpty(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    /**
     * Verica se o valor é diferente de nulo, caso seja o Consumer aceita o argumento.
     *
     * @param value  O valor a ser vericado.
     * @param setter caso não venha nulo ele aceita o argumento.
     */
    private void setValueIfNotNull(BigDecimal value, Consumer<BigDecimal> setter) {
        if (value != null) {
            setter.accept(value);
        }
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