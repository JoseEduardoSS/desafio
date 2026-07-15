package jose.eduardo.desafio.domain.exception;

/**
 * Lançada quando um fornecedor referenciado não existe.
 */
public class FornecedorNaoEncontradoException extends RuntimeException {

    public FornecedorNaoEncontradoException(Long id) {
        super("Fornecedor não encontrado para o id " + id + ".");
    }
}
