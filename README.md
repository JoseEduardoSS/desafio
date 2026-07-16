# Contas a Pagar — API

API REST para gestão de contas a pagar: cadastro, listagem paginada com filtros,
relatório de total pago por período, importação assíncrona de contas via CSV e
autenticação com JWT.

**Stack:** Java 21 · Spring Boot 3.5 · Spring Security (JWT) · Spring Data JPA ·
PostgreSQL · Flyway · RabbitMQ · Swagger (springdoc-openapi).

---

## Como executar

Pré-requisito: **Docker** com Docker Compose. Na raiz do projeto:

```bash
docker-compose up --build
```

Isso sobe **PostgreSQL**, **RabbitMQ** e a **aplicação**, já conectados. A app só
inicia quando o banco e o broker estão prontos, e o Flyway cria o schema e cadastra
os fornecedores automaticamente.

Para encerrar: `docker-compose down` (use `-v` para apagar também os dados do banco).

---

## Como usar (Swagger)

Toda a API pode ser testada pela interface do Swagger:

**http://localhost:8080/swagger-ui.html**

Passo a passo:

1. Em **Autenticação → POST /api/auth/login**, execute com o usuário padrão
   `admin` / `admin123` e copie o `accessToken` da resposta.
2. Clique em **Authorize** (canto superior direito), cole o token e confirme.
3. Pronto — os endpoints de **Contas** e **Importação** já podem ser testados.

Os fornecedores `1` (Alpha) e `2` (Beta) já vêm cadastrados para usar nos exemplos.
Para a importação, há um arquivo pronto em `contas-exemplo.csv`.

Outras URLs úteis:

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Painel do RabbitMQ | http://localhost:15672 (`guest` / `guest`) |

---

## Decisões arquiteturais

**Arquitetura hexagonal / DDD.** Quatro camadas com dependências apontando para o domínio:

- `domain` — modelo, regras de negócio, interfaces de repositório e exceções.
  **Sem dependência de framework** (o modelo não tem anotações JPA).
- `application` — serviços de caso de uso e _commands_.
- `infrastructure` — adaptadores: persistência JPA, mensageria RabbitMQ, segurança.
- `interfaces` — controllers REST e DTOs.

As interfaces de repositório ficam no domínio e são implementadas na infraestrutura
(inversão de dependência), mantendo as regras de negócio isoladas e testáveis.

**Domínio rico.** A entidade `Conta` não expõe setters: o estado só muda por fábricas
(`criarNova`, que valida; `reconstituir`, que confia no dado persistido) e métodos de
negócio. Invariantes como "conta paga sempre tem data de pagamento" valem em qualquer
caminho de entrada (API ou CSV). A situação segue uma máquina de estados (`CANCELADO`
é terminal).

**Validação em profundidade:** contrato (Bean Validation → `400`), fluxo
(existência de fornecedor/conta → `404`) e domínio (invariantes e transições → `409`).
Um `@RestControllerAdvice` central traduz cada erro no status HTTP adequado.

**Performance JPA.** Associação `Conta → Fornecedor` é `LAZY` com `@EntityGraph` nas
consultas de listagem/busca (evita o problema Select N+1). O relatório usa projeção
com agregação (`SUM`/`COUNT`) executada no banco.

**Importação assíncrona e resiliente.** O upload valida e publica no RabbitMQ,
retornando um protocolo na hora. O _consumer_ processa linha a linha: uma linha
inválida é ignorada sem abortar o arquivo. Há fila de _dead-letter_ configurada.

**Segurança stateless com JWT.** A app atua como _resource server_; tokens são
assinados/verificados com chave simétrica (HS256). Rotas de autenticação e da
documentação são públicas, o restante exige token.

---

## Testes

```bash
./mvnw test
```

Cobrem as regras de negócio (invariantes da conta, máquina de estados, parser do CSV,
serviços e _value objects_). Requerem PostgreSQL e RabbitMQ disponíveis.
