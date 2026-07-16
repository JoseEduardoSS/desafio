package jose.eduardo.desafio.domain.model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PeriodoTest {

    @Test
    void shouldCriarPeriodoQuandoInicioAnteriorOuIgualAoFim() {
        assertThatCode(() -> new Periodo(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31)))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldLancarExcecaoQuandoInicioPosteriorAoFim() {
        assertThatThrownBy(() -> new Periodo(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 7, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldLancarExcecaoQuandoDatasNulas() {
        assertThatThrownBy(() -> new Periodo(null, LocalDate.of(2026, 7, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
