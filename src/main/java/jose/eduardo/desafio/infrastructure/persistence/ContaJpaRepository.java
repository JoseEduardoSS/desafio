package jose.eduardo.desafio.infrastructure.persistence;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jose.eduardo.desafio.domain.model.SituacaoConta;

public interface ContaJpaRepository extends JpaRepository<ContaJpaEntity, Long>, JpaSpecificationExecutor<ContaJpaEntity> {

    @Override
    @EntityGraph(attributePaths = "fornecedor")
    Page<ContaJpaEntity> findAll(Specification<ContaJpaEntity> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "fornecedor")
    Optional<ContaJpaEntity> findById(Long id);

    @Query("""
            SELECT COALESCE(SUM(c.valor), 0) AS total, COUNT(c) AS quantidade
            FROM ContaJpaEntity c
            WHERE c.situacao = :situacao
              AND c.dataPagamento BETWEEN :inicio AND :fim
            """)
    TotalPagoProjection totalPagoNoPeriodo(@Param("situacao") SituacaoConta situacao,
                                           @Param("inicio") LocalDate inicio,
                                           @Param("fim") LocalDate fim);
}
