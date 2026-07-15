package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório Spring Data para {@link FornecedorJpaEntity}.
 */
public interface FornecedorJpaRepository extends JpaRepository<FornecedorJpaEntity, Long> {
}
