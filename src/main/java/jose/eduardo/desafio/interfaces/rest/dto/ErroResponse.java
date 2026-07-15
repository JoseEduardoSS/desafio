package jose.eduardo.desafio.interfaces.rest.dto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Corpo padronizado de resposta de erro da API.
 */
public record ErroResponse(
        OffsetDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        List<CampoInvalido> camposInvalidos) {

    public record CampoInvalido(String campo, String mensagem) {
    }

    public static ErroResponse of(int status, String erro, String mensagem) {
        return new ErroResponse(OffsetDateTime.now(), status, erro, mensagem, List.of());
    }

    public static ErroResponse of(int status, String erro, String mensagem, List<CampoInvalido> campos) {
        return new ErroResponse(OffsetDateTime.now(), status, erro, mensagem, campos);
    }
}
