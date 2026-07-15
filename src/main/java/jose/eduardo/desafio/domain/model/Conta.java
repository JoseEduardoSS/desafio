package jose.eduardo.desafio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade de domínio que representa uma conta a pagar.
 *
 * <p>A identidade é dada pelo {@code id}; por isso a igualdade
 * ({@code equals}/{@code hashCode}) é baseada apenas nesse campo.</p>
 *
 * <p>Invariante: toda conta pertence obrigatoriamente a um {@link Fornecedor}.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Conta {

    private Long id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String descricao;
    private SituacaoConta situacao;
    private Fornecedor fornecedor;

    /**
     * Altera a situação da conta aplicando as regras de negócio associadas:
     * ao marcar como {@link SituacaoConta#PAGO} registra a data de pagamento
     * (hoje, se ainda não houver); nos demais estados a data de pagamento é
     * limpa, pois a conta deixa de estar quitada.
     *
     * @param novaSituacao nova situação; não pode ser nula
     */
    public void alterarSituacao(SituacaoConta novaSituacao) {
        if (novaSituacao == null) {
            throw new IllegalArgumentException("A nova situação não pode ser nula.");
        }
        if (novaSituacao == SituacaoConta.PAGO) {
            if (this.dataPagamento == null) {
                this.dataPagamento = LocalDate.now();
            }
        } else {
            this.dataPagamento = null;
        }
        this.situacao = novaSituacao;
    }
}
