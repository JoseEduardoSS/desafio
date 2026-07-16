package jose.eduardo.desafio.interfaces.rest.dto;

import java.util.List;
import java.util.function.Function;

import jose.eduardo.desafio.domain.pagination.Pagina;

/**
 * Representação de saída de um resultado paginado.
 *
 * @param conteudo       elementos da página atual
 * @param pagina         número da página, iniciando em zero
 * @param tamanho        tamanho da página
 * @param totalElementos total de elementos que satisfazem o filtro
 * @param totalPaginas   total de páginas disponíveis
 * @param <T>            tipo dos elementos de saída
 */
public record PaginaResponse<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas) {

    /**
     * Constrói a resposta a partir de uma {@link Pagina} do domínio, convertendo
     * cada elemento com o {@code conversor} informado.
     */
    public static <D, T> PaginaResponse<T> from(Pagina<D> pagina, Function<? super D, ? extends T> conversor) {
        return new PaginaResponse<>(
                pagina.conteudo().stream().<T>map(conversor).toList(),
                pagina.pagina(),
                pagina.tamanho(),
                pagina.totalElementos(),
                pagina.totalPaginas());
    }
}
