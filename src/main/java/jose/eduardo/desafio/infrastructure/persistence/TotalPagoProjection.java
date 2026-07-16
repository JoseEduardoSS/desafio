package jose.eduardo.desafio.infrastructure.persistence;

import java.math.BigDecimal;

public interface TotalPagoProjection {

    BigDecimal getTotal();

    long getQuantidade();
}
