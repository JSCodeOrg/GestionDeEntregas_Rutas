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
    
    // Nuevas constantes para notificaciones
    public static final String QUEUE_NOTIFICACIONES = "cola_notificaciones";
    public static final String ROUTING_KEY_NOTIFICACION_ASIGNACION = "notificacion.asignacion";
    
    @Bean
    public Queue queuePedidos() {
        return new Queue(QUEUE_PEDIDOS, true);
    }

    @Bean
    public Queue queueEntregas() {
        return new Queue(QUEUE_ENTREGAS, true);
    }

    @Bean
    public Queue queueNotificaciones() {
        return new Queue(QUEUE_NOTIFICACIONES, true);
    }

    @Bean
    public TopicExchange exchangePedidos() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public TopicExchange exchangeEntregas() {
        return new TopicExchange(EXCHANGE_ENTREGAS);
    }

    @Bean
    public Binding bindingPedidos(Queue queuePedidos, TopicExchange exchangePedidos) {
        return BindingBuilder.bind(queuePedidos).to(exchangePedidos).with(ROUTING_KEY_PEDIDO_CREADO);
    }

    @Bean
    public Binding bindingEntregas(Queue queueEntregas, TopicExchange exchangeEntregas) {
        return BindingBuilder.bind(queueEntregas).to(exchangeEntregas).with(ROUTING_KEY_ENTREGA_ASIGNADA);
    }

    @Bean
    public Binding bindingNotificaciones(Queue queueNotificaciones, TopicExchange exchangeEntregas) {
        return BindingBuilder.bind(queueNotificaciones).to(exchangeEntregas).with(ROUTING_KEY_NOTIFICACION_ASIGNACION);
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