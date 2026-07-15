package jose.eduardo.desafio.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jose.eduardo.desafio.application.dto.AtualizarContaCommand;
import jose.eduardo.desafio.application.dto.CriarContaCommand;
import jose.eduardo.desafio.domain.model.SituacaoConta;

/**
 * Payload de entrada para criação e atualização de uma conta.
 *
 * @param situacao opcional; na criação, se ausente, assume {@code PENDENTE}.
 */
public record ContaRequest(
        @NotNull(message = "A data de vencimento é obrigatória.")
        LocalDate dataVencimento,

        LocalDate dataPagamento,

        @NotNull(message = "O valor é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero.")
        BigDecimal valor,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
        String descricao,

        SituacaoConta situacao,

        @NotNull(message = "O fornecedor é obrigatório.")
        Long fornecedorId) {

    public CriarContaCommand toCriarCommand() {
        return new CriarContaCommand(dataVencimento, dataPagamento, valor, descricao, situacao, fornecedorId);
    }

    public AtualizarContaCommand toAtualizarCommand() {
        return new AtualizarContaCommand(dataVencimento, dataPagamento, valor, descricao, situacao, fornecedorId);
    }
}
