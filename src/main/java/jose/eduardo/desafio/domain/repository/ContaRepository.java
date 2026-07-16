package jose.eduardo.desafio.domain.repository;

import java.util.Optional;

import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.FiltroConta;
import jose.eduardo.desafio.domain.model.Periodo;
import jose.eduardo.desafio.domain.model.RelatorioTotalPago;
import jose.eduardo.desafio.domain.pagination.Pagina;
import jose.eduardo.desafio.domain.pagination.Paginacao;

public interface ContaRepository {

    Conta salvar(Conta conta);

    Optional<Conta> buscarPorId(Long id);

    Pagina<Conta> buscar(FiltroConta filtro, Paginacao paginacao);

    RelatorioTotalPago totalPagoNoPeriodo(Periodo periodo);

    boolean existePorId(Long id);

    void removerPorId(Long id);
}
