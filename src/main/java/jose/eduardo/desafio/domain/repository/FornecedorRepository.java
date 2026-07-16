package jose.eduardo.desafio.domain.repository;

import java.util.List;
import java.util.Optional;

import jose.eduardo.desafio.domain.model.Fornecedor;

public interface FornecedorRepository {

    Fornecedor salvar(Fornecedor fornecedor);

    Optional<Fornecedor> buscarPorId(Long id);

    List<Fornecedor> listarTodos();
}
