package br.com.fiap.rabbitmq_demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    //Injeta o RabbitTeplate e  os valores de configuração via construtor
    public MessageProducer(RabbitTemplate rabbitTemplate,
                           @Value("${app.rabbitmq.exchange}") String exchangeName,
                           @Value("${app.rabbitmq.routingkey}") String routingKey){
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void sendMessage (String message){
        log.info("Enviando mensagem: '{}' para exchange '{}' com routing key '{}'",
                message, exchangeName, routingKey);
        //Usa o rabbittemplate ára enviar a mensagem
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
        log.info("Mensagem enviada");
    }

}
