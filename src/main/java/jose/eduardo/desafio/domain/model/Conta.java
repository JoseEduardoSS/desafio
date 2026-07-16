package jose.eduardo.desafio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jose.eduardo.desafio.domain.exception.TransicaoSituacaoInvalidaException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
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

    private Conta() {
    }

    public static Conta criarNova(LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor,
                                  String descricao, SituacaoConta situacao, Fornecedor fornecedor) {
        Conta conta = new Conta();
        conta.definirDados(dataVencimento, valor, descricao, fornecedor);
        conta.situacao = situacao != null ? situacao : SituacaoConta.PENDENTE;
        conta.ajustarDataPagamento(dataPagamento);
        return conta;
    }

    public static Conta reconstituir(Long id, LocalDate dataVencimento, LocalDate dataPagamento,
                                     BigDecimal valor, String descricao, SituacaoConta situacao,
                                     Fornecedor fornecedor) {
        Conta conta = new Conta();
        conta.id = id;
        conta.dataVencimento = dataVencimento;
        conta.dataPagamento = dataPagamento;
        conta.valor = valor;
        conta.descricao = descricao;
        conta.situacao = situacao;
        conta.fornecedor = fornecedor;
        return conta;
    }

    public void alterarDados(LocalDate dataVencimento, BigDecimal valor, String descricao, Fornecedor fornecedor) {
        definirDados(dataVencimento, valor, descricao, fornecedor);
    }

    public void alterarSituacao(SituacaoConta novaSituacao) {
        alterarSituacao(novaSituacao, null);
    }

    public void alterarSituacao(SituacaoConta novaSituacao, LocalDate dataPagamentoInformada) {
        if (novaSituacao == null) {
            throw new IllegalArgumentException("A nova situação não pode ser nula.");
        }
        if (!this.situacao.podeTransicionarPara(novaSituacao)) {
            throw new TransicaoSituacaoInvalidaException(this.situacao, novaSituacao);
        }
        this.situacao = novaSituacao;
        ajustarDataPagamento(dataPagamentoInformada);
    }

    private void definirDados(LocalDate dataVencimento, BigDecimal valor, String descricao, Fornecedor fornecedor) {
        if (dataVencimento == null) {
            throw new IllegalArgumentException("A data de vencimento é obrigatória.");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if (fornecedor == null) {
            throw new IllegalArgumentException("O fornecedor é obrigatório.");
        }
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
    }

    private void ajustarDataPagamento(LocalDate dataPagamentoInformada) {
        if (this.situacao.exigeDataPagamento()) {
            LocalDate pagamento = dataPagamentoInformada != null ? dataPagamentoInformada
                    : this.dataPagamento != null ? this.dataPagamento : LocalDate.now();
            if (pagamento.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("A data de pagamento não pode estar no futuro.");
            }
            this.dataPagamento = pagamento;
        } else {
            this.dataPagamento = null;
        }
    }
}
