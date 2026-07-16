package jose.eduardo.desafio.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jose.eduardo.desafio.application.messaging.ImportacaoContaMessage;
import jose.eduardo.desafio.application.messaging.ImportacaoContaPublisher;

@ExtendWith(MockitoExtension.class)
class ImportacaoContaServiceTest {

    @Mock
    private ImportacaoContaPublisher publisher;

    @InjectMocks
    private ImportacaoContaService service;

    @Test
    void shouldPublicarMensagemERetornarProtocolo() {
        String protocolo = service.importar("contas.csv", "cabecalho\nlinha1");

        ArgumentCaptor<ImportacaoContaMessage> captor = ArgumentCaptor.forClass(ImportacaoContaMessage.class);
        verify(publisher).publicar(captor.capture());

        assertThat(protocolo).isNotBlank();
        assertThat(captor.getValue().protocolo()).isEqualTo(protocolo);
        assertThat(captor.getValue().nomeArquivo()).isEqualTo("contas.csv");
        assertThat(captor.getValue().conteudo()).isEqualTo("cabecalho\nlinha1");
    }

    @Test
    void shouldLancarExcecaoQuandoConteudoVazio() {
        assertThatThrownBy(() -> service.importar("contas.csv", "   "))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(publisher);
    }
}
