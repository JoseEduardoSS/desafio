/**
 * Camada de Domínio — Paginação.
 *
 * <p>Abstrações de paginação ({@link jose.eduardo.desafio.domain.pagination.Paginacao}
 * e {@link jose.eduardo.desafio.domain.pagination.Pagina}) usadas pelas portas de
 * repositório. Mantêm o domínio livre de tipos de infraestrutura (como
 * {@code Pageable}/{@code Page} do Spring Data), cuja tradução ocorre nos
 * adaptadores.</p>
 */
package jose.eduardo.desafio.domain.pagination;
