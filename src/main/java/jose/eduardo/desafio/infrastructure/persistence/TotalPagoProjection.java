package jose.eduardo.desafio.infrastructure.persistence;

import java.math.BigDecimal;

/**
 * Projeção do resultado agregado da consulta de total pago por período.
 *
 * <p>Os nomes dos getters correspondem aos aliases da consulta JPQL em
 * {@link ContaJpaRepository#totalPagoNoPeriodo}.</p>
 */
public interface TotalPagoProjection {

    BigDecimal getTotal();

    long getQuantidade();
}
