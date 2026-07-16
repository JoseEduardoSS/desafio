package jose.eduardo.desafio.domain.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PaginacaoTest {

    @Test
    void shouldAplicarValoresPadraoQuandoNulos() {
        Paginacao paginacao = Paginacao.de(null, null);

        assertThat(paginacao.pagina()).isEqualTo(Paginacao.PAGINA_PADRAO);
        assertThat(paginacao.tamanho()).isEqualTo(Paginacao.TAMANHO_PADRAO);
    }

    @Test
    void shouldLancarExcecaoQuandoPaginaNegativa() {
        assertThatThrownBy(() -> new Paginacao(-1, 20))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldLancarExcecaoQuandoTamanhoAcimaDoMaximo() {
        assertThatThrownBy(() -> new Paginacao(0, Paginacao.TAMANHO_MAXIMO + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
