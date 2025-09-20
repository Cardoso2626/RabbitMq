package br.com.fiap.rabbitmq_demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {
    @Value("${app.rabbitmq.exchange}")
    private String exchangeName; //Ve em qual fila deve ser enviado
    @Value("${app.rabbitmq.queue}")
    private String queueName; //Organiza fila
    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    @Bean // bean escuta para atender em tempo de execução a fila, ele vai colocar o objeto na fila certa, se o receptor receber, ele tira essa fila e entrega uma cópia
    Queue queue(){
        //Criar a fila. Por padrão é durável, não exclusiva, não auto-delete
        return new Queue(queueName, true);
    }

    @Bean
    DirectExchange exchange(){
        // Cria a  direct exchange
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        //Criar a binding entre a queue e a exchange usando a routing key
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
