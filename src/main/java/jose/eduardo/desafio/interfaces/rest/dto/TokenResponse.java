package jose.eduardo.desafio.interfaces.rest.dto;

import jose.eduardo.desafio.infrastructure.security.JwtTokenService.TokenEmitido;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn) {

    public static TokenResponse from(TokenEmitido emitido) {
        return new TokenResponse(emitido.token(), "Bearer", emitido.expiraEmSegundos());
    }
}
