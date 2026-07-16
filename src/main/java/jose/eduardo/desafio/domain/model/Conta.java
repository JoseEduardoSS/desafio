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
