package jose.eduardo.desafio.infrastructure.persistence;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jose.eduardo.desafio.domain.model.SituacaoConta;

/**
 * Repositório Spring Data para {@link ContaJpaEntity}.
 *
 * <p>Estende {@link JpaSpecificationExecutor} para permitir consultas dinâmicas
 * (filtros combináveis) com paginação.</p>
 */
public interface ContaJpaRepository extends JpaRepository<ContaJpaEntity, Long>, JpaSpecificationExecutor<ContaJpaEntity> {

    /**
     * Agrega o total pago (soma dos valores) e a quantidade de contas na
     * situação informada cuja data de pagamento esteja no intervalo.
     * O {@code COALESCE} garante zero quando não há contas no período.
     */
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
