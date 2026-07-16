package jose.eduardo.desafio.infrastructure.messaging;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jose.eduardo.desafio.application.command.CriarContaCommand;
import jose.eduardo.desafio.domain.model.SituacaoConta;

@Component
public class ContaCsvParser {

    private static final int COLUNAS_ESPERADAS = 6;

    public CriarContaCommand parseLinha(String linha) {
        String[] colunas = linha.split(",", -1);
        if (colunas.length < COLUNAS_ESPERADAS) {
            throw new IllegalArgumentException(
                    "Esperadas " + COLUNAS_ESPERADAS + " colunas, encontradas " + colunas.length + ".");
        }

        LocalDate dataVencimento = parseDataObrigatoria(colunas[0], "dataVencimento");
        LocalDate dataPagamento = parseDataOpcional(colunas[1]);
        BigDecimal valor = parseValor(colunas[2]);
        String descricao = vazioParaNull(colunas[3]);
        SituacaoConta situacao = parseSituacaoOpcional(colunas[4]);
        Long fornecedorId = parseFornecedorId(colunas[5]);

        return new CriarContaCommand(dataVencimento, dataPagamento, valor, descricao, situacao, fornecedorId);
    }

    private LocalDate parseDataObrigatoria(String valor, String campo) {
        String texto = valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("O campo '" + campo + "' é obrigatório.");
        }
        return LocalDate.parse(texto);
    }

    private LocalDate parseDataOpcional(String valor) {
        String texto = valor.trim();
        return texto.isEmpty() ? null : LocalDate.parse(texto);
    }

    private BigDecimal parseValor(String valor) {
        String texto = valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("O campo 'valor' é obrigatório.");
        }
        return new BigDecimal(texto);
    }

    private SituacaoConta parseSituacaoOpcional(String valor) {
        String texto = valor.trim();
        return texto.isEmpty() ? null : SituacaoConta.valueOf(texto.toUpperCase());
    }

    private Long parseFornecedorId(String valor) {
        String texto = valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("O campo 'fornecedorId' é obrigatório.");
        }
        return Long.valueOf(texto);
    }

    private String vazioParaNull(String valor) {
        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
    }
}
