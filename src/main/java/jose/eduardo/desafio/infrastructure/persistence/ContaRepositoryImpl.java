package jose.eduardo.desafio.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.repository.ContaRepository;

/**
 * Adaptador JPA que implementa a porta {@link ContaRepository}.
 */
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
        // Referência gerenciada ao fornecedor (proxy) — evita insert/update indesejado.
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
    public List<Conta> listarTodas() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
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
