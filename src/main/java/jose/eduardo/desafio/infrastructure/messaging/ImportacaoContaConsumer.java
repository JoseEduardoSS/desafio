package jose.eduardo.desafio.infrastructure.messaging;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jose.eduardo.desafio.application.command.CriarContaCommand;
import jose.eduardo.desafio.application.messaging.ImportacaoContaMessage;
import jose.eduardo.desafio.application.service.ContaService;
import jose.eduardo.desafio.infrastructure.config.RabbitConfig;

@Slf4j
@Component
public class ImportacaoContaConsumer {

    private final ContaService contaService;
    private final ContaCsvParser parser;

    public ImportacaoContaConsumer(ContaService contaService, ContaCsvParser parser) {
        this.contaService = contaService;
        this.parser = parser;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void processar(ImportacaoContaMessage mensagem) {
        String protocolo = mensagem.protocolo();
        log.info("Iniciando importação protocolo={} arquivo={}", protocolo, mensagem.nomeArquivo());

        List<String> linhas = mensagem.conteudo().lines().toList();
        int sucesso = 0;
        int falha = 0;

        for (int i = 1; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            if (linha.isBlank()) {
                continue;
            }
            int numeroLinha = i + 1;
            try {
                CriarContaCommand comando = parser.parseLinha(linha);
                contaService.criar(comando);
                sucesso++;
            } catch (Exception e) {
                falha++;
                log.warn("Falha ao importar linha {} (protocolo={}): {}", numeroLinha, protocolo, e.getMessage());
            }
        }

        log.info("Importação concluída protocolo={} sucesso={} falha={}", protocolo, sucesso, falha);
    }
}
