package jose.eduardo.desafio.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.RelatorioTotalPago;

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
