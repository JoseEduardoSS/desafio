package jose.eduardo.desafio.domain.model;

import java.math.BigDecimal;

/**
 * Resultado do relatório de total pago em um {@link Periodo}.
 *
 * <p>Consolida quanto foi efetivamente pago (soma dos valores das contas na
 * situação {@link SituacaoConta#PAGO}, pela data de pagamento) e quantas contas
 * compõem esse total. Quando não há contas no período, o total é
 * {@link BigDecimal#ZERO}.</p>
 *
 * @param periodo    intervalo considerado
 * @param totalPago  soma dos valores pagos no período
 * @param quantidade número de contas que compõem o total
 */
public record RelatorioTotalPago(Periodo periodo, BigDecimal totalPago, long quantidade) {

    public RelatorioTotalPago {
        if (periodo == null) {
            throw new IllegalArgumentException("O período do relatório é obrigatório.");
        }
        totalPago = totalPago != null ? totalPago : BigDecimal.ZERO;
    }
}
