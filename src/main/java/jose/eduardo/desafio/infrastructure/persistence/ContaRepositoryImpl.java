package jose.eduardo.desafio.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.FiltroConta;
import jose.eduardo.desafio.domain.model.Periodo;
import jose.eduardo.desafio.domain.model.RelatorioTotalPago;
import jose.eduardo.desafio.domain.model.SituacaoConta;
import jose.eduardo.desafio.domain.pagination.Pagina;
import jose.eduardo.desafio.domain.pagination.Paginacao;
import jose.eduardo.desafio.domain.repository.ContaRepository;

@Repository
public class ContaRepositoryImpl implements ContaRepository {

    private final ContaJpaRepository jpaRepository;
    private final FornecedorJpaRepository fornecedorJpaRepository;
    private final ContaPersistenceMapper mapper;

    public ContaRepositoryImpl(ContaJpaRepository jpaRepository,
                               FornecedorJpaRepository fornecedorJpaRepository,
                               ContaPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.fornecedorJpaRepository = fornecedorJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Conta salvar(Conta conta) {
        FornecedorJpaEntity fornecedorRef =
                fornecedorJpaRepository.getReferenceById(conta.getFornecedor().getId());
        ContaJpaEntity salvo = jpaRepository.save(mapper.toJpa(conta, fornecedorRef));
        return mapper.toDomain(salvo);
    }

    @Override
    public Optional<Conta> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Pagina<Conta> buscar(FiltroConta filtro, Paginacao paginacao) {
        Pageable pageable = PageRequest.of(
                paginacao.pagina(),
                paginacao.tamanho(),
                Sort.by(Sort.Direction.ASC, "dataVencimento"));

        Page<ContaJpaEntity> pagina =
                jpaRepository.findAll(ContaSpecifications.comFiltro(filtro), pageable);

        List<Conta> conteudo = pagina.getContent().stream().map(mapper::toDomain).toList();
        return new Pagina<>(
                conteudo,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages());
    }

    @Override
    public RelatorioTotalPago totalPagoNoPeriodo(Periodo periodo) {
        TotalPagoProjection projecao = jpaRepository.totalPagoNoPeriodo(
                SituacaoConta.PAGO, periodo.inicio(), periodo.fim());
        return new RelatorioTotalPago(periodo, projecao.getTotal(), projecao.getQuantidade());
    }

    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void removerPorId(Long id) {
        jpaRepository.deleteById(id);
    }
}
