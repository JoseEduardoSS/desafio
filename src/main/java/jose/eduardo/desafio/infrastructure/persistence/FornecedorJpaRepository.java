package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorJpaRepository extends JpaRepository<FornecedorJpaEntity, Long> {
}
