package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repositório Spring Data para {@link ContaJpaEntity}.
 *
 * <p>Estende {@link JpaSpecificationExecutor} para permitir consultas dinâmicas
 * (filtros combináveis) com paginação.</p>
 */
public interface ContaJpaRepository extends JpaRepository<ContaJpaEntity, Long>, JpaSpecificationExecutor<ContaJpaEntity> {
}
