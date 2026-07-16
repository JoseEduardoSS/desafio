package jose.eduardo.desafio.domain.repository;

import java.util.Optional;

import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.FiltroConta;
import jose.eduardo.desafio.domain.model.Periodo;
import jose.eduardo.desafio.domain.model.RelatorioTotalPago;
import jose.eduardo.desafio.domain.pagination.Pagina;
import jose.eduardo.desafio.domain.pagination.Paginacao;

/**
 * Porta de persistência do agregado {@link Conta}.
 *
 * <p>A implementação concreta fica na camada de infraestrutura, mantendo o
 * domínio independente de tecnologia.</p>
 */
public interface ContaRepository {

    Conta salvar(Conta conta);

    Optional<Conta> buscarPorId(Long id);

    /**
     * Busca contas aplicando o {@link FiltroConta} informado e retornando o
     * resultado de acordo com a {@link Paginacao} solicitada.
     */
    Pagina<Conta> buscar(FiltroConta filtro, Paginacao paginacao);

    /**
     * Consolida o total pago (contas na situação {@code PAGO}, pela data de
     * pagamento) dentro do {@link Periodo} informado.
     */
    RelatorioTotalPago totalPagoNoPeriodo(Periodo periodo);

    boolean existePorId(Long id);

    void removerPorId(Long id);
}
