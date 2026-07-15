package jose.eduardo.desafio.interfaces.rest.dto;

import jose.eduardo.desafio.domain.model.Fornecedor;

/**
 * Representação de saída de um fornecedor.
 */
public record FornecedorResponse(Long id, String nome) {

    public static FornecedorResponse from(Fornecedor fornecedor) {
        if (fornecedor == null) {
            return null;
        }
        return new FornecedorResponse(fornecedor.getId(), fornecedor.getNome());
    }
}
