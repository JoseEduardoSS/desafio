package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.stereotype.Component;

import jose.eduardo.desafio.domain.model.Conta;

/**
 * Conversão entre o modelo de domínio {@link Conta} e a entidade persistente
 * {@link ContaJpaEntity}.
 */
@Component
public class ContaPersistenceMapper {

    private final FornecedorPersistenceMapper fornecedorMapper;

    public ContaPersistenceMapper(FornecedorPersistenceMapper fornecedorMapper) {
        this.fornecedorMapper = fornecedorMapper;
    }

    public Conta toDomain(ContaJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Conta.builder()
                .id(entity.getId())
                .dataVencimento(entity.getDataVencimento())
                .dataPagamento(entity.getDataPagamento())
                .valor(entity.getValor())
                .descricao(entity.getDescricao())
                .situacao(entity.getSituacao())
                .fornecedor(fornecedorMapper.toDomain(entity.getFornecedor()))
                .build();
    }

    /**
     * Converte o domínio em entidade JPA. O {@code fornecedor} é fornecido como
     * referência gerenciada para que a associação {@code @ManyToOne} não tente
     * inserir/atualizar o fornecedor.
     */
    public ContaJpaEntity toJpa(Conta conta, FornecedorJpaEntity fornecedorRef) {
        if (conta == null) {
            return null;
        }
        ContaJpaEntity entity = new ContaJpaEntity();
        entity.setId(conta.getId());
        entity.setDataVencimento(conta.getDataVencimento());
        entity.setDataPagamento(conta.getDataPagamento());
        entity.setValor(conta.getValor());
        entity.setDescricao(conta.getDescricao());
        entity.setSituacao(conta.getSituacao());
        entity.setFornecedor(fornecedorRef);
        return entity;
    }
}
