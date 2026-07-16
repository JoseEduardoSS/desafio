package jose.eduardo.desafio.infrastructure.persistence;

import org.springframework.stereotype.Component;

import jose.eduardo.desafio.domain.model.Conta;

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
        return Conta.reconstituir(
                entity.getId(),
                entity.getDataVencimento(),
                entity.getDataPagamento(),
                entity.getValor(),
                entity.getDescricao(),
                entity.getSituacao(),
                fornecedorMapper.toDomain(entity.getFornecedor()));
    }

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
