package jose.eduardo.desafio.domain.exception;

public class FornecedorNaoEncontradoException extends RuntimeException {
    public FornecedorNaoEncontradoException(Long id) {
        super("Fornecedor não encontrado para o id " + id + ".");
    }
}
