package jose.eduardo.desafio.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jose.eduardo.desafio.application.messaging.ImportacaoContaMessage;
import jose.eduardo.desafio.application.messaging.ImportacaoContaPublisher;

@Service
public class ImportacaoContaService {

    private final ImportacaoContaPublisher publisher;

    public ImportacaoContaService(ImportacaoContaPublisher publisher) {
        this.publisher = publisher;
    }

    public String importar(String nomeArquivo, String conteudo) {
        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("O arquivo CSV está vazio.");
        }
        String protocolo = UUID.randomUUID().toString();
        publisher.publicar(new ImportacaoContaMessage(protocolo, nomeArquivo, conteudo));
        return protocolo;
    }
}
