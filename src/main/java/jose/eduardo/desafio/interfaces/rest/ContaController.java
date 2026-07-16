package jose.eduardo.desafio.interfaces.rest;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jose.eduardo.desafio.application.service.ContaService;
import jose.eduardo.desafio.domain.model.Conta;
import jose.eduardo.desafio.domain.model.FiltroConta;
import jose.eduardo.desafio.domain.model.Periodo;
import jose.eduardo.desafio.domain.pagination.Paginacao;
import jose.eduardo.desafio.interfaces.rest.dto.AtualizarSituacaoRequest;
import jose.eduardo.desafio.interfaces.rest.dto.ContaRequest;
import jose.eduardo.desafio.interfaces.rest.dto.ContaResponse;
import jose.eduardo.desafio.interfaces.rest.dto.PaginaResponse;
import jose.eduardo.desafio.interfaces.rest.dto.RelatorioTotalPagoResponse;

@Tag(name = "Contas", description = "Cadastro, consulta, filtros e relatório de contas a pagar")
@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @Operation(summary = "Cria uma nova conta a pagar")
    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request,
                                               UriComponentsBuilder uriBuilder) {
        Conta conta = contaService.criar(request.toCriarCommand());
        URI location = uriBuilder.path("/api/contas/{id}").buildAndExpand(conta.getId()).toUri();
        return ResponseEntity.created(location).body(ContaResponse.from(conta));
    }

    @Operation(summary = "Lista contas de forma paginada, com filtros por descrição e vencimento")
    @GetMapping
    public PaginaResponse<ContaResponse> listar(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoFim,
            @RequestParam(required = false) Integer pagina,
            @RequestParam(required = false) Integer tamanho) {

        FiltroConta filtro = new FiltroConta(descricao, dataVencimentoInicio, dataVencimentoFim);
        Paginacao paginacao = Paginacao.de(pagina, tamanho);
        return PaginaResponse.from(contaService.listar(filtro, paginacao), ContaResponse::from);
    }

    @Operation(summary = "Retorna o total pago e a quantidade de contas em um período")
    @GetMapping("/relatorios/total-pago")
    public RelatorioTotalPagoResponse totalPago(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Periodo periodo = new Periodo(inicio, fim);
        return RelatorioTotalPagoResponse.from(contaService.totalPagoNoPeriodo(periodo));
    }

    @Operation(summary = "Busca uma conta pelo identificador")
    @GetMapping("/{id}")
    public ContaResponse buscarPorId(@PathVariable Long id) {
        return ContaResponse.from(contaService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza os dados de uma conta existente")
    @PutMapping("/{id}")
    public ContaResponse atualizar(@PathVariable Long id, @Valid @RequestBody ContaRequest request) {
        return ContaResponse.from(contaService.atualizar(id, request.toAtualizarCommand()));
    }

    @Operation(summary = "Altera a situação da conta (respeita a máquina de estados; 409 se inválida)")
    @PatchMapping("/{id}/situacao")
    public ContaResponse alterarSituacao(@PathVariable Long id,
                                         @Valid @RequestBody AtualizarSituacaoRequest request) {
        return ContaResponse.from(contaService.alterarSituacao(id, request.situacao()));
    }

    @Operation(summary = "Remove uma conta pelo identificador")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        contaService.remover(id);
    }
}
