package jose.eduardo.desafio.domain.repository;

import java.util.List;
import java.util.Optional;

import jose.eduardo.desafio.domain.model.Conta;

/**
 * Porta de persistência do agregado {@link Conta}.
 *
 * <p>A implementação concreta fica na camada de infraestrutura, mantendo o
 * domínio independente de tecnologia.</p>
 */
public interface ContaRepository {

    Conta salvar(Conta conta);

    Optional<Conta> buscarPorId(Long id);

    List<Conta> listarTodas();

    boolean existePorId(Long id);

    void removerPorId(Long id);
}
