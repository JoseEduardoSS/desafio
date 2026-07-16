package jose.eduardo.desafio.domain.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(Long id) {
        super("Conta não encontrada para o id " + id + ".");
    }
}
