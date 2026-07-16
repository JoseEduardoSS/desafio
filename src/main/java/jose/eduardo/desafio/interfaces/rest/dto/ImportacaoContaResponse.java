package jose.eduardo.desafio.interfaces.rest.dto;

/**
 * Resposta do upload de importação: devolve o protocolo de acompanhamento.
 *
 * @param protocolo identificador único da importação enfileirada
 * @param mensagem  texto informativo sobre o enfileiramento
 */
public record ImportacaoContaResponse(String protocolo, String mensagem) {
}
