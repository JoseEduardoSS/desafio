package jose.eduardo.desafio.interfaces.rest.dto;

import jakarta.validation.constraints.NotNull;
import jose.eduardo.desafio.domain.model.SituacaoConta;

public record AtualizarSituacaoRequest(
        @NotNull(message = "A situação é obrigatória.")
        SituacaoConta situacao) {
}
