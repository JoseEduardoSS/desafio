package jose.eduardo.desafio.domain.model;

import java.util.Set;

public enum SituacaoConta {

    PENDENTE,
    PAGO,
    CANCELADO;

    public Set<SituacaoConta> transicoesPermitidas() {
        return switch (this) {
            case PENDENTE -> Set.of(PAGO, CANCELADO);
            case PAGO -> Set.of(PENDENTE, CANCELADO);
            case CANCELADO -> Set.of();
        };
    }

    public boolean podeTransicionarPara(SituacaoConta novaSituacao) {
        return this == novaSituacao || transicoesPermitidas().contains(novaSituacao);
    }

    public boolean exigeDataPagamento() {
        return this == PAGO;
    }
}
