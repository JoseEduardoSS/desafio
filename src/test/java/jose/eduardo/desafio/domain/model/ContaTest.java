package jose.eduardo.desafio.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ContaTest {

    @Test
    void shouldRegistrarDataPagamentoAoMarcarComoPago() {
        Conta conta = Conta.builder().situacao(SituacaoConta.PENDENTE).build();

        conta.alterarSituacao(SituacaoConta.PAGO);

        assertThat(conta.getSituacao()).isEqualTo(SituacaoConta.PAGO);
        assertThat(conta.getDataPagamento()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldManterDataPagamentoExistenteAoMarcarComoPago() {
        LocalDate pagamentoOriginal = LocalDate.of(2026, 1, 10);
        Conta conta = Conta.builder()
                .situacao(SituacaoConta.PENDENTE)
                .dataPagamento(pagamentoOriginal)
                .build();

        conta.alterarSituacao(SituacaoConta.PAGO);

        assertThat(conta.getDataPagamento()).isEqualTo(pagamentoOriginal);
    }

    @Test
    void shouldLimparDataPagamentoAoSairDePago() {
        Conta conta = Conta.builder()
                .situacao(SituacaoConta.PAGO)
                .dataPagamento(LocalDate.of(2026, 1, 10))
                .build();

        conta.alterarSituacao(SituacaoConta.CANCELADO);

        assertThat(conta.getSituacao()).isEqualTo(SituacaoConta.CANCELADO);
        assertThat(conta.getDataPagamento()).isNull();
    }

    @Test
    void shouldLancarExcecaoAoAlterarSituacaoParaNulo() {
        Conta conta = Conta.builder().situacao(SituacaoConta.PENDENTE).build();

        assertThatThrownBy(() -> conta.alterarSituacao(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
