package jose.eduardo.desafio.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jose.eduardo.desafio.domain.model.Fornecedor;
import jose.eduardo.desafio.domain.repository.FornecedorRepository;

@Configuration
public class CargaInicialFornecedores {

    @Bean
    CommandLineRunner seedFornecedores(FornecedorRepository fornecedorRepository) {
        return args -> {
            if (fornecedorRepository.listarTodos().isEmpty()) {
                fornecedorRepository.salvar(Fornecedor.builder().nome("Fornecedor Alpha").build());
                fornecedorRepository.salvar(Fornecedor.builder().nome("Fornecedor Beta").build());
            }
        };
    }
}
