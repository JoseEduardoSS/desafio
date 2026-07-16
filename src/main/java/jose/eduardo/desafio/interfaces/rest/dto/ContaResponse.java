package jose.eduardo.desafio.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.SituacaoConta;

public record ContaResponse(
        Long id,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        BigDecimal valor,
        String descricao,
        SituacaoConta situacao,
        FornecedorResponse fornecedor) {

    public static ContaResponse from(Conta conta) {
        return new ContaResponse(
                conta.getId(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getValor(),
                conta.getDescricao(),
                conta.getSituacao(),
                FornecedorResponse.from(conta.getFornecedor()));
    }
}
