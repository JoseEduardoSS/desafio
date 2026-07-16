package jose.eduardo.desafio.domain.exception;

import jose.eduardo.desafio.domain.model.SituacaoConta;

public class TransicaoSituacaoInvalidaException extends RuntimeException {

    public TransicaoSituacaoInvalidaException(SituacaoConta atual, SituacaoConta novaSituacao) {
        super("Não é permitido alterar a situação de " + atual + " para " + novaSituacao + ".");
    }
}
