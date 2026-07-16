package jose.eduardo.desafio.domain.pagination;

public record Paginacao(int pagina, int tamanho) {

    public static final int PAGINA_PADRAO = 0;
    public static final int TAMANHO_PADRAO = 20;
    public static final int TAMANHO_MAXIMO = 100;

    public Paginacao {
        if (pagina < 0) {
            throw new IllegalArgumentException("A página não pode ser negativa.");
        }
        if (tamanho < 1) {
            throw new IllegalArgumentException("O tamanho da página deve ser maior que zero.");
        }
        if (tamanho > TAMANHO_MAXIMO) {
            throw new IllegalArgumentException("O tamanho da página não pode exceder " + TAMANHO_MAXIMO + ".");
        }
    }

    public static Paginacao de(Integer pagina, Integer tamanho) {
        return new Paginacao(
                pagina != null ? pagina : PAGINA_PADRAO,
                tamanho != null ? tamanho : TAMANHO_PADRAO);
    }
}
