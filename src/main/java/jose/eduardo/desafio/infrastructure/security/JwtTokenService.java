package jose.eduardo.desafio.infrastructure.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private static final String ISSUER = "desafio";

    private final JwtEncoder encoder;
    private final long expiracaoMinutos;

    public JwtTokenService(JwtEncoder encoder,
                           @Value("${app.security.jwt.expiration-minutes}") long expiracaoMinutos) {
        this.encoder = encoder;
        this.expiracaoMinutos = expiracaoMinutos;
    }

    public TokenEmitido gerar(Authentication autenticacao) {
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(expiracaoMinutos, ChronoUnit.MINUTES);

        String escopos = autenticacao.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(agora)
                .expiresAt(expiracao)
                .subject(autenticacao.getName())
                .claim("scope", escopos)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new TokenEmitido(token, expiracaoMinutos * 60);
    }

    public record TokenEmitido(String token, long expiraEmSegundos) {
    }
}
