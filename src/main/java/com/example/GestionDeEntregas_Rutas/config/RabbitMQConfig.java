package com.example.GestionDeEntregas_Rutas.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PEDIDOS = "cola_pedidos";
    public static final String EXCHANGE_PEDIDOS = "exchange_pedidos";
    public static final String ROUTING_KEY_PEDIDO_CREADO = "pedido.creado";

    public static final String QUEUE_ENTREGAS = "cola_entregas";
    public static final String EXCHANGE_ENTREGAS = "exchange_entregas";
    public static final String ROUTING_KEY_ENTREGA_ASIGNADA = "entrega.asignada";
    
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_PEDIDOS, true);

    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PEDIDO_CREADO);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}