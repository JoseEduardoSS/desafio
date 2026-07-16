package jose.eduardo.desafio.interfaces.rest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jose.eduardo.desafio.infrastructure.security.JwtTokenService;
import jose.eduardo.desafio.infrastructure.security.JwtTokenService.TokenEmitido;
import jose.eduardo.desafio.interfaces.rest.dto.LoginRequest;
import jose.eduardo.desafio.interfaces.rest.dto.TokenResponse;

@Tag(name = "Autenticação", description = "Emissão de token JWT para acesso à API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Autentica o usuário e retorna um token JWT")
    @SecurityRequirements
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication autenticacao = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        TokenEmitido token = tokenService.gerar(autenticacao);
        return TokenResponse.from(token);
    }
}
