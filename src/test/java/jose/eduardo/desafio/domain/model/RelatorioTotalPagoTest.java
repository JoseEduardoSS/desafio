package jose.eduardo.desafio.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class RelatorioTotalPagoTest {

    private final Periodo periodo = new Periodo(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31));

    @Test
    void shouldUsarZeroQuandoTotalPagoNulo() {
        RelatorioTotalPago relatorio = new RelatorioTotalPago(periodo, null, 0);

        assertThat(relatorio.totalPago()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldManterTotalInformadoQuandoPresente() {
        RelatorioTotalPago relatorio = new RelatorioTotalPago(periodo, new BigDecimal("4250.00"), 12);

        assertThat(relatorio.totalPago()).isEqualByComparingTo("4250.00");
        assertThat(relatorio.quantidade()).isEqualTo(12);
    }

    @Test
    void shouldLancarExcecaoQuandoPeriodoNulo() {
        assertThatThrownBy(() -> new RelatorioTotalPago(null, BigDecimal.TEN, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
