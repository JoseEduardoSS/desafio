package jose.eduardo.desafio.domain.repository;

import java.util.Optional;

import jose.eduardo.desafio.domain.model.Fornecedor;

public interface FornecedorRepository {

    Optional<Fornecedor> buscarPorId(Long id);
}
