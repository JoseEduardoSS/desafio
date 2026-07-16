package jose.eduardo.desafio.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jose.eduardo.desafio.domain.model.Fornecedor;
import jose.eduardo.desafio.domain.repository.FornecedorRepository;

@Repository
public class FornecedorRepositoryImpl implements FornecedorRepository {

    private final FornecedorJpaRepository jpaRepository;
    private final FornecedorPersistenceMapper mapper;

    public FornecedorRepositoryImpl(FornecedorJpaRepository jpaRepository, FornecedorPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Fornecedor salvar(Fornecedor fornecedor) {
        FornecedorJpaEntity salvo = jpaRepository.save(mapper.toJpa(fornecedor));
        return mapper.toDomain(salvo);
    }

    @Override
    public Optional<Fornecedor> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Fornecedor> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
