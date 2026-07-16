package jose.eduardo.desafio.domain.model;

import java.time.LocalDate;

/**
 * Objeto de valor que expressa os critérios de busca de {@link Conta}.
 *
 * <p>Todos os campos são opcionais; um critério nulo (ou, no caso da descrição,
 * em branco) simplesmente não é aplicado. O filtro por {@code dataVencimento} é
 * um intervalo fechado — informar a mesma data em início e fim filtra um único
 * dia.</p>
 *
 * @param descricao            trecho a ser buscado na descrição (parcial, sem
 *                             diferenciar maiúsculas de minúsculas)
 * @param dataVencimentoInicio limite inferior (inclusivo) do vencimento
 * @param dataVencimentoFim    limite superior (inclusivo) do vencimento
 */
public record FiltroConta(
        String descricao,
        LocalDate dataVencimentoInicio,
        LocalDate dataVencimentoFim) {

    public FiltroConta {
        if (dataVencimentoInicio != null
                && dataVencimentoFim != null
                && dataVencimentoInicio.isAfter(dataVencimentoFim)) {
            throw new IllegalArgumentException("A data de vencimento inicial não pode ser posterior à final.");
        }
    }

    public boolean temDescricao() {
        return descricao != null && !descricao.isBlank();
    }

    public boolean temDataVencimentoInicio() {
        return dataVencimentoInicio != null;
    }

    public boolean temDataVencimentoFim() {
        return dataVencimentoFim != null;
    }
}
