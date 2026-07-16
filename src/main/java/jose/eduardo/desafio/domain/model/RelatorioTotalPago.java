package jose.eduardo.desafio.domain.model;

import java.math.BigDecimal;

public record RelatorioTotalPago(Periodo periodo, BigDecimal totalPago, long quantidade) {

    public RelatorioTotalPago {
        if (periodo == null) {
            throw new IllegalArgumentException("O período do relatório é obrigatório.");
        }
        totalPago = totalPago != null ? totalPago : BigDecimal.ZERO;
    }
}
