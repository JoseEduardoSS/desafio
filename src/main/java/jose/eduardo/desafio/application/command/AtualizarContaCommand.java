package jose.eduardo.desafio.application.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.SituacaoConta;

public record AtualizarContaCommand(
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        BigDecimal valor,
        String descricao,
        SituacaoConta situacao,
        Long fornecedorId) {
}
