package jose.eduardo.desafio.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import jose.eduardo.desafio.domain.exception.TransicaoSituacaoInvalidaException;

class ContaTest {

    private static final Fornecedor FORNECEDOR = Fornecedor.builder().id(1L).nome("Alpha").build();
    private static final LocalDate VENCIMENTO = LocalDate.of(2026, 7, 10);
    private static final BigDecimal VALOR = new BigDecimal("150.00");

    private static Conta pendente() {
        return Conta.criarNova(VENCIMENTO, null, VALOR, "Energia", SituacaoConta.PENDENTE, FORNECEDOR);
    }

    @Test
    void shouldAssumirPendenteQuandoSituacaoNaoInformada() {
        Conta conta = Conta.criarNova(VENCIMENTO, null, VALOR, "Energia", null, FORNECEDOR);

        assertThat(conta.getSituacao()).isEqualTo(SituacaoConta.PENDENTE);
        assertThat(conta.getDataPagamento()).isNull();
    }

    @Test
    void shouldExigirDataPagamentoAoCriarComoPago() {
        Conta conta = Conta.criarNova(VENCIMENTO, null, VALOR, "Energia", SituacaoConta.PAGO, FORNECEDOR);

        assertThat(conta.getDataPagamento()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldRejeitarValorNaoPositivo() {
        assertThatThrownBy(() ->
                Conta.criarNova(VENCIMENTO, null, BigDecimal.ZERO, "Energia", null, FORNECEDOR))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejeitarFornecedorNulo() {
        assertThatThrownBy(() ->
                Conta.criarNova(VENCIMENTO, null, VALOR, "Energia", null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejeitarDataPagamentoNoFuturo() {
        LocalDate futuro = LocalDate.now().plusDays(1);

        assertThatThrownBy(() ->
                Conta.criarNova(VENCIMENTO, futuro, VALOR, "Energia", SituacaoConta.PAGO, FORNECEDOR))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRegistrarDataPagamentoAoMarcarComoPago() {
        Conta conta = pendente();

        conta.alterarSituacao(SituacaoConta.PAGO);

        assertThat(conta.getSituacao()).isEqualTo(SituacaoConta.PAGO);
        assertThat(conta.getDataPagamento()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldLimparDataPagamentoAoSairDePago() {
        Conta conta = pendente();
        conta.alterarSituacao(SituacaoConta.PAGO);

        conta.alterarSituacao(SituacaoConta.PENDENTE);

        assertThat(conta.getSituacao()).isEqualTo(SituacaoConta.PENDENTE);
        assertThat(conta.getDataPagamento()).isNull();
    }

    @Test
    void shouldImpedirAlterarSituacaoDeContaCancelada() {
        Conta conta = pendente();
        conta.alterarSituacao(SituacaoConta.CANCELADO);

        assertThatThrownBy(() -> conta.alterarSituacao(SituacaoConta.PAGO))
                .isInstanceOf(TransicaoSituacaoInvalidaException.class);
    }

    @Test
    void shouldLancarExcecaoAoAlterarSituacaoParaNulo() {
        Conta conta = pendente();

        assertThatThrownBy(() -> conta.alterarSituacao(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
