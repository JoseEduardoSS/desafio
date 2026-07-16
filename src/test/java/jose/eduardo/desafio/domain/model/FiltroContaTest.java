package jose.eduardo.desafio.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class FiltroContaTest {

    @Test
    void shouldLancarExcecaoQuandoIntervaloDeVencimentoInvertido() {
        assertThatThrownBy(() -> new FiltroConta("energia",
                LocalDate.of(2026, 8, 1), LocalDate.of(2026, 7, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldIndicarAusenciaDeDescricaoQuandoEmBranco() {
        FiltroConta filtro = new FiltroConta("   ", null, null);

        assertThat(filtro.temDescricao()).isFalse();
    }

    @Test
    void shouldIndicarPresencaDeDescricaoQuandoPreenchida() {
        FiltroConta filtro = new FiltroConta("energia", null, null);

        assertThat(filtro.temDescricao()).isTrue();
    }
}
