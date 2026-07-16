package jose.eduardo.desafio.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.RelatorioTotalPago;

/**
 * Representação de saída do relatório de total pago por período.
 *
 * @param inicio     data inicial do período (inclusiva)
 * @param fim        data final do período (inclusiva)
 * @param totalPago  soma dos valores pagos no período
 * @param quantidade número de contas que compõem o total
 */
public record RelatorioTotalPagoResponse(
        LocalDate inicio,
        LocalDate fim,
        BigDecimal totalPago,
        long quantidade) {

    public static RelatorioTotalPagoResponse from(RelatorioTotalPago relatorio) {
        return new RelatorioTotalPagoResponse(
                relatorio.periodo().inicio(),
                relatorio.periodo().fim(),
                relatorio.totalPago(),
                relatorio.quantidade());
    }
}
