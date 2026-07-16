package jose.eduardo.desafio.application.messaging;

public interface ImportacaoContaPublisher {

    void publicar(ImportacaoContaMessage mensagem);
}
