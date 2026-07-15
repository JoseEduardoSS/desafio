package jose.eduardo.desafio.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.SituacaoConta;

/**
 * Dados de entrada para atualização completa de uma conta.
 */
public record AtualizarContaCommand(
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        BigDecimal valor,
        String descricao,
        SituacaoConta situacao,
        Long fornecedorId) {
}
