package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.stereotype.Component;

import jose.eduardo.desafio.domain.model.Fornecedor;

/**
 * Conversão entre o modelo de domínio {@link Fornecedor} e a entidade
 * persistente {@link FornecedorJpaEntity}.
 */
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
