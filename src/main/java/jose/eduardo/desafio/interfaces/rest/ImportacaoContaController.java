package jose.eduardo.desafio.interfaces.rest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jose.eduardo.desafio.application.service.ImportacaoContaService;
import jose.eduardo.desafio.interfaces.rest.dto.ImportacaoContaResponse;

@Tag(name = "Importação", description = "Importação assíncrona de contas via arquivo CSV")
@RestController
@RequestMapping("/api/contas/importacoes")
public class ImportacaoContaController {

    private final ImportacaoContaService importacaoContaService;

    public ImportacaoContaController(ImportacaoContaService importacaoContaService) {
        this.importacaoContaService = importacaoContaService;
    }

    @Operation(summary = "Recebe um CSV e enfileira a importação, retornando um protocolo")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ImportacaoContaResponse importar(@RequestParam("arquivo") MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("O arquivo é obrigatório e não pode estar vazio.");
        }

        String conteudo = lerConteudo(arquivo);
        String protocolo = importacaoContaService.importar(arquivo.getOriginalFilename(), conteudo);

        return new ImportacaoContaResponse(protocolo, "Importação recebida e enfileirada para processamento.");
    }

    private String lerConteudo(MultipartFile arquivo) {
        try {
            return new String(arquivo.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler o arquivo enviado.", e);
        }
    }
}
