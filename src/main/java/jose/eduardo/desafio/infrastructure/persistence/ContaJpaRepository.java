package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório Spring Data para {@link ContaJpaEntity}.
 */
public interface ContaJpaRepository extends JpaRepository<ContaJpaEntity, Long> {
}
