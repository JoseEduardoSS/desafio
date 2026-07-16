package jose.eduardo.desafio.domain.model;

import java.time.LocalDate;

public record Periodo(LocalDate inicio, LocalDate fim) {

    public Periodo {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("O período exige data de início e de fim.");
        }
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
    }
}
