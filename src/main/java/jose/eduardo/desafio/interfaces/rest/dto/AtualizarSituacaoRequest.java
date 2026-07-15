package jose.eduardo.desafio.interfaces.rest.dto;

import jakarta.validation.constraints.NotNull;
import jose.eduardo.desafio.domain.model.SituacaoConta;

/**
 * Payload para alteração da situação (status) de uma conta.
 */
public record AtualizarSituacaoRequest(
        @NotNull(message = "A situação é obrigatória.")
        SituacaoConta situacao) {
}
