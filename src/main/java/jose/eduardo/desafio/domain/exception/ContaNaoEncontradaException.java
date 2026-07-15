package jose.eduardo.desafio.domain.exception;

/**
 * Lançada quando uma conta referenciada não existe.
 */
public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(Long id) {
        super("Conta não encontrada para o id " + id + ".");
    }
}
