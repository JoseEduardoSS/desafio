package jose.eduardo.desafio.infrastructure.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import jose.eduardo.desafio.application.command.ContaCommand;
import jose.eduardo.desafio.domain.model.SituacaoConta;

class ContaCsvParserTest {

    private final ContaCsvParser parser = new ContaCsvParser();

    @Test
    void shouldConverterLinhaValidaEmComando() {
        ContaCommand comando =
                parser.parseLinha("2026-07-15,2026-07-14,320.50,Aluguel,PAGO,1");

        assertThat(comando.dataVencimento()).isEqualTo(LocalDate.of(2026, 7, 15));
        assertThat(comando.dataPagamento()).isEqualTo(LocalDate.of(2026, 7, 14));
        assertThat(comando.valor()).isEqualByComparingTo("320.50");
        assertThat(comando.descricao()).isEqualTo("Aluguel");
        assertThat(comando.situacao()).isEqualTo(SituacaoConta.PAGO);
        assertThat(comando.fornecedorId()).isEqualTo(1L);
    }

    @Test
    void shouldTratarCamposOpcionaisVaziosComoNulos() {
        ContaCommand comando =
                parser.parseLinha("2026-07-10,,150.00,,,2");

        assertThat(comando.dataPagamento()).isNull();
        assertThat(comando.descricao()).isNull();
        assertThat(comando.situacao()).isNull();
    }

    @Test
    void shouldLancarExcecaoQuandoColunasInsuficientes() {
        assertThatThrownBy(() -> parser.parseLinha("2026-07-10,,150.00"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldLancarExcecaoQuandoValorNaoNumerico() {
        assertThatThrownBy(() -> parser.parseLinha("2026-07-10,,abc,Descricao,PENDENTE,1"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
