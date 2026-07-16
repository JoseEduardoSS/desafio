package jose.eduardo.desafio.domain.pagination;

import java.util.List;
import java.util.function.Function;

/**
 * Resultado paginado genérico, independente de framework.
 *
 * @param conteudo        elementos da página atual
 * @param pagina          número da página, iniciando em zero
 * @param tamanho         tamanho solicitado para a página
 * @param totalElementos  total de elementos que satisfazem o filtro
 * @param totalPaginas    total de páginas disponíveis
 * @param <T>             tipo dos elementos
 */
public record Pagina<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas) {

    /**
     * Converte o conteúdo desta página preservando os metadados de paginação.
     */
    public <R> Pagina<R> map(Function<? super T, ? extends R> conversor) {
        List<R> convertido = conteudo.stream().<R>map(conversor).toList();
        return new Pagina<>(convertido, pagina, tamanho, totalElementos, totalPaginas);
    }
}
