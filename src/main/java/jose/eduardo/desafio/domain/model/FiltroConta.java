package jose.eduardo.desafio.domain.model;

import java.time.LocalDate;

public record FiltroConta(
        String descricao,
        LocalDate dataVencimentoInicio,
        LocalDate dataVencimentoFim) {

    public FiltroConta {
        if (dataVencimentoInicio != null
                && dataVencimentoFim != null
                && dataVencimentoInicio.isAfter(dataVencimentoFim)) {
            throw new IllegalArgumentException("A data de vencimento inicial não pode ser posterior à final.");
        }
    }

    public boolean temDescricao() {
        return descricao != null && !descricao.isBlank();
    }

    public boolean temDataVencimentoInicio() {
        return dataVencimentoInicio != null;
    }

    public boolean temDataVencimentoFim() {
        return dataVencimentoFim != null;
    }
}
