package jose.eduardo.desafio.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import jose.eduardo.desafio.application.messaging.ImportacaoContaMessage;
import jose.eduardo.desafio.application.messaging.ImportacaoContaPublisher;
import jose.eduardo.desafio.infrastructure.config.RabbitConfig;

@Component
public class RabbitImportacaoContaPublisher implements ImportacaoContaPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitImportacaoContaPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicar(ImportacaoContaMessage mensagem) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, mensagem);
    }
}
