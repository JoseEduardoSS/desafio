package jose.eduardo.desafio.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jose.eduardo.desafio.application.command.ContaCommand;
import jose.eduardo.desafio.domain.exception.ContaNaoEncontradaException;
import jose.eduardo.desafio.domain.exception.FornecedorNaoEncontradoException;
import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.Fornecedor;
import jose.eduardo.desafio.domain.model.SituacaoConta;
import jose.eduardo.desafio.domain.repository.ContaRepository;
import jose.eduardo.desafio.domain.repository.FornecedorRepository;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ContaService service;

    @Test
    void shouldCriarContaComSituacaoPendenteQuandoNaoInformada() {
        when(fornecedorRepository.buscarPorId(1L))
                .thenReturn(Optional.of(Fornecedor.builder().id(1L).nome("Alpha").build()));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(inv -> inv.getArgument(0));

        ContaCommand comando = new ContaCommand(
                LocalDate.of(2026, 7, 10), null, new BigDecimal("150.00"), "Energia", null, 1L);

        service.criar(comando);

        ArgumentCaptor<Conta> captor = ArgumentCaptor.forClass(Conta.class);
        verify(contaRepository).salvar(captor.capture());
        assertThat(captor.getValue().getSituacao()).isEqualTo(SituacaoConta.PENDENTE);
    }

    @Test
    void shouldLancarExcecaoAoCriarComFornecedorInexistente() {
        when(fornecedorRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        ContaCommand comando = new ContaCommand(
                LocalDate.of(2026, 7, 10), null, new BigDecimal("150.00"), "Energia", null, 999L);

        assertThatThrownBy(() -> service.criar(comando))
                .isInstanceOf(FornecedorNaoEncontradoException.class);
        verify(contaRepository, never()).salvar(any());
    }

    @Test
    void shouldLancarExcecaoAoBuscarContaInexistente() {
        when(contaRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(1L))
                .isInstanceOf(ContaNaoEncontradaException.class);
    }

    @Test
    void shouldLancarExcecaoAoRemoverContaInexistente() {
        when(contaRepository.existePorId(1L)).thenReturn(false);

        assertThatThrownBy(() -> service.remover(1L))
                .isInstanceOf(ContaNaoEncontradaException.class);
        verify(contaRepository, never()).removerPorId(any());
    }
}
