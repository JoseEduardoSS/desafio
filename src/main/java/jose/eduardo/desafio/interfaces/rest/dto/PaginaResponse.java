package jose.eduardo.desafio.interfaces.rest.dto;

import java.util.List;
import java.util.function.Function;

import jose.eduardo.desafio.domain.pagination.Pagina;

public record PaginaResponse<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas) {

    public static <D, T> PaginaResponse<T> from(Pagina<D> pagina, Function<? super D, ? extends T> conversor) {
        return new PaginaResponse<>(
                pagina.conteudo().stream().<T>map(conversor).toList(),
                pagina.pagina(),
                pagina.tamanho(),
                pagina.totalElementos(),
                pagina.totalPaginas());
    }
}
