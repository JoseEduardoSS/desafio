package jose.eduardo.desafio.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import jose.eduardo.desafio.domain.model.FiltroConta;

final class ContaSpecifications {

    private ContaSpecifications() {
    }

    static Specification<ContaJpaEntity> comFiltro(FiltroConta filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            if (filtro.temDescricao()) {
                predicados.add(cb.like(
                        cb.lower(root.get("descricao")),
                        "%" + filtro.descricao().toLowerCase() + "%"));
            }
            if (filtro.temDataVencimentoInicio()) {
                predicados.add(cb.greaterThanOrEqualTo(
                        root.get("dataVencimento"), filtro.dataVencimentoInicio()));
            }
            if (filtro.temDataVencimentoFim()) {
                predicados.add(cb.lessThanOrEqualTo(
                        root.get("dataVencimento"), filtro.dataVencimentoFim()));
            }

            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
