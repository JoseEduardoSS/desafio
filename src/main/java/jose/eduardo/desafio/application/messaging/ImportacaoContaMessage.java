package jose.eduardo.desafio.application.messaging;

/**
 * Mensagem que trafega no broker representando uma solicitação de importação de
 * contas em lote.
 *
 * <p>Carrega o conteúdo bruto do CSV; o parsing e a inserção acontecem no
 * consumidor, mantendo a publicação (e o endpoint) leves.</p>
 *
 * @param protocolo   identificador único da importação, devolvido ao cliente
 * @param nomeArquivo nome original do arquivo enviado (para rastreio/log)
 * @param conteudo    conteúdo textual do CSV (UTF-8)
 */
public record ImportacaoContaMessage(String protocolo, String nomeArquivo, String conteudo) {
}
