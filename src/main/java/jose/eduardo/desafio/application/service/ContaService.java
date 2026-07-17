package jose.eduardo.desafio.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jose.eduardo.desafio.application.command.ContaCommand;
import jose.eduardo.desafio.domain.exception.ContaNaoEncontradaException;
import jose.eduardo.desafio.domain.exception.FornecedorNaoEncontradoException;
import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.FiltroConta;
import jose.eduardo.desafio.domain.model.Fornecedor;
import jose.eduardo.desafio.domain.model.Periodo;
import jose.eduardo.desafio.domain.model.RelatorioTotalPago;
import jose.eduardo.desafio.domain.model.SituacaoConta;
import jose.eduardo.desafio.domain.pagination.Pagina;
import jose.eduardo.desafio.domain.pagination.Paginacao;
import jose.eduardo.desafio.domain.repository.ContaRepository;
import jose.eduardo.desafio.domain.repository.FornecedorRepository;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ContaService(ContaRepository contaRepository, FornecedorRepository fornecedorRepository) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Conta criar(ContaCommand comando) {
        Fornecedor fornecedor = buscarFornecedor(comando.fornecedorId());

        Conta conta = Conta.criarNova(
                comando.dataVencimento(),
                comando.dataPagamento(),
                comando.valor(),
                comando.descricao(),
                comando.situacao(),
                fornecedor);

        return contaRepository.salvar(conta);
    }

    @Transactional(readOnly = true)
    public Pagina<Conta> listar(FiltroConta filtro, Paginacao paginacao) {
        return contaRepository.buscar(filtro, paginacao);
    }

    @Transactional(readOnly = true)
    public RelatorioTotalPago totalPagoNoPeriodo(Periodo periodo) {
        return contaRepository.totalPagoNoPeriodo(periodo);
    }

    @Transactional(readOnly = true)
    public Conta buscarPorId(Long id) {
        return contaRepository.buscarPorId(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id));
    }

    @Transactional
    public Conta atualizar(Long id, ContaCommand comando) {
        Conta conta = buscarPorId(id);
        Fornecedor fornecedor = buscarFornecedor(comando.fornecedorId());

        conta.alterarDados(comando.dataVencimento(), comando.valor(), comando.descricao(), fornecedor);
        if (comando.situacao() != null) {
            conta.alterarSituacao(comando.situacao(), comando.dataPagamento());
        }

        return contaRepository.salvar(conta);
    }

    @Transactional
    public Conta alterarSituacao(Long id, SituacaoConta novaSituacao) {
        Conta conta = buscarPorId(id);
        conta.alterarSituacao(novaSituacao);
        return contaRepository.salvar(conta);
    }

    @Transactional
    public void remover(Long id) {
        if (!contaRepository.existePorId(id)) {
            throw new ContaNaoEncontradaException(id);
        }
        contaRepository.removerPorId(id);
    }

    private Fornecedor buscarFornecedor(Long fornecedorId) {
        return Optional.ofNullable(fornecedorId)
                .flatMap(fornecedorRepository::buscarPorId)
                .orElseThrow(() -> new FornecedorNaoEncontradoException(fornecedorId));
    }
}
