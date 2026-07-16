package jose.eduardo.desafio.application.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.SituacaoConta;

/**
 * Dados de entrada para criação de uma conta.
 *
 * @param situacao situação inicial; se nula, a conta assume {@code PENDENTE}.
 */
public record CriarContaCommand(
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        BigDecimal valor,
        String descricao,
        SituacaoConta situacao,
        Long fornecedorId) {
}
