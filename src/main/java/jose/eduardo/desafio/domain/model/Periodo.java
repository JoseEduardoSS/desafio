package jose.eduardo.desafio.domain.model;

import java.time.LocalDate;

/**
 * Objeto de valor que representa um intervalo de datas fechado (inclusivo nas
 * duas pontas).
 *
 * <p>Garante por construção a invariante de que o início nunca é posterior ao
 * fim, evitando períodos inválidos em qualquer ponto do sistema.</p>
 *
 * @param inicio data inicial (inclusiva)
 * @param fim    data final (inclusiva)
 */
public record Periodo(LocalDate inicio, LocalDate fim) {

    public Periodo {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("O período exige data de início e de fim.");
        }
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "A data de início não pode ser posterior à data de fim.");
        }
    }
}
