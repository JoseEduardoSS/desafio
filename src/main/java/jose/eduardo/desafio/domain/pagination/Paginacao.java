package jose.eduardo.desafio.domain.pagination;

/**
 * Objeto de valor que descreve a fatia de resultados desejada.
 *
 * <p>Vive no domínio para que a porta de repositório permaneça independente de
 * qualquer tecnologia de persistência (ex.: {@code Pageable} do Spring Data). A
 * tradução para o mecanismo concreto acontece na camada de infraestrutura.</p>
 *
 * @param pagina  número da página, iniciando em zero
 * @param tamanho quantidade de elementos por página
 */
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
            throw new IllegalArgumentException(
                    "O tamanho da página não pode exceder " + TAMANHO_MAXIMO + ".");
        }
    }

    /**
     * Cria uma paginação a partir de valores opcionais, aplicando os padrões
     * quando ausentes.
     */
    public static Paginacao de(Integer pagina, Integer tamanho) {
        return new Paginacao(
                pagina != null ? pagina : PAGINA_PADRAO,
                tamanho != null ? tamanho : TAMANHO_PADRAO);
    }
}
