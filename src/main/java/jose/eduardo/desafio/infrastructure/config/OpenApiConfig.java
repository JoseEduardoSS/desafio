package jose.eduardo.desafio.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA_JWT = "bearer-jwt";

    @Bean
    OpenAPI contasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Contas a Pagar")
                        .version("v1")
                        .description("""
                                API para gestão de contas a pagar: cadastro, listagem paginada com filtros,
                                relatório de total pago por período e importação assíncrona via CSV.
                                Autentique-se em POST /api/auth/login e use o token no botão Authorize.""")
                        .contact(new Contact().name("José Eduardo Souza")))
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA_JWT))
                .components(new Components().addSecuritySchemes(ESQUEMA_JWT,
                        new SecurityScheme()
                                .name(ESQUEMA_JWT)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
