package jose.eduardo.desafio.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mapeamento persistente (JPA) do {@link jose.eduardo.desafio.domain.model.Fornecedor}.
 */
@Entity
@Table(name = "fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
}
