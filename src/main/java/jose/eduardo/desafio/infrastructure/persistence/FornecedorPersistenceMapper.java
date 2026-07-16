package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.stereotype.Component;

import jose.eduardo.desafio.domain.model.Fornecedor;

@Component
public class FornecedorPersistenceMapper {

    public Fornecedor toDomain(FornecedorJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Fornecedor.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    public FornecedorJpaEntity toJpa(Fornecedor fornecedor) {
        if (fornecedor == null) {
            return null;
        }
        return new FornecedorJpaEntity(fornecedor.getId(), fornecedor.getNome());
    }
}
