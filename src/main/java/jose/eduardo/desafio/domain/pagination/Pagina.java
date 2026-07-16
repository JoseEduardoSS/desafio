package jose.eduardo.desafio.domain.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagina<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas) {

    public <R> Pagina<R> map(Function<? super T, ? extends R> conversor) {
        List<R> convertido = conteudo.stream().<R>map(conversor).toList();
        return new Pagina<>(convertido, pagina, tamanho, totalElementos, totalPaginas);
    }
}
